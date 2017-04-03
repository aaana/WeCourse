package com.weike.java.service.wx;

import com.weike.java.DAO.wx.CourseDAO;
import com.weike.java.DAO.wx.WxQuestionDAO;
import com.weike.java.entity.wx.Course;
import com.weike.java.entity.wx.WxQuestion;
import com.weike.java.entity.wx.WxQuestionCell;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by tina on 3/19/17.
 */
@Service("wxQuestionService")
@Transactional
public class WxQuestionServiceImpl implements WxQuestionService {
    @Autowired
    private WxQuestionDAO wxQuestionDAO;

    @Autowired
    private CourseDAO courseDAO;

    public WxQuestionCell createQuestion(WxQuestion wxQuestion) {
        int id = wxQuestionDAO.save(wxQuestion);
        wxQuestion.setId(id);
        return new WxQuestionCell(wxQuestion);
    }

    public WxQuestion getSimpleQuestionWithId(int id) {
        return wxQuestionDAO.findQuestionWithId(id);
    }

    public WxQuestionCell getQuestionWithId(int id) {
        return new WxQuestionCell(wxQuestionDAO.findQuestionWithId(id));
    }

    public List<WxQuestionCell> getAllQuestionWithCourseId(int course_id) {
        List<WxQuestion> wxQuestions = wxQuestionDAO.findAllQuestionWithCourseId(course_id);
        return transWxQUestion2QuestionCell(wxQuestions, true);
    }

    public List<WxQuestionCell> getAllQuestionWithFirstQuestionId(int wxQuestion_id, int user_id) {
        List<WxQuestion> wxQuestions = wxQuestionDAO.findAllQuestionWithFirstQuestionId(wxQuestion_id);
        List<WxQuestionCell> wxQuestionCells = transWxQUestion2QuestionCell(wxQuestions, false);
        return transWxQUestionCell2WellPreparedWxQuestionCell(wxQuestionCells, user_id, wxQuestion_id);
    }

    public List<WxQuestionCell> getAllQuestionWithRaiserId(int user_id) {
        List<WxQuestion> wxQuestions = wxQuestionDAO.findAllQuestionWithRaiserId(user_id);
        return transWxQUestion2QuestionCell(wxQuestions, true);
    }

    public List<WxQuestionCell> transWxQUestion2QuestionCell(List<WxQuestion> wxQuestions, Boolean hasAnswerNum) {
        List<WxQuestionCell> wxQuestionCells = new LinkedList<WxQuestionCell>();
        for (WxQuestion wxQuestion : wxQuestions) {
            WxQuestionCell wxQuestionCell = new WxQuestionCell(wxQuestion);
            if (hasAnswerNum) {
                int answer_num = wxQuestionDAO.getAnswerNumWithQuestionId(wxQuestion.getId());
                wxQuestionCell.setAnswer_num(answer_num);
            }
            wxQuestionCells.add(wxQuestionCell);
        }
        return wxQuestionCells;
    }

    public List<WxQuestionCell> transWxQUestionCell2WellPreparedWxQuestionCell(List<WxQuestionCell> wxQuestionCells, int user_id, int wxQuestion_id) {
        if (wxQuestionCells.size() != 0) {
            int course_id = wxQuestionCells.get(0).getCourse_id();
            Course course = courseDAO.findCourseById(course_id);
            int teacher_id = course.getUser_id();
            WxQuestion wxQuestion = wxQuestionDAO.findQuestionWithId(wxQuestion_id);
            int asker_id = wxQuestion.getPublisher_id();

            // 添加level_num publisher_name
            int level_num = 0;
            int pubName = 97;
            Map<Integer, String> publisherIdNameMap = new HashMap<Integer, String>();
            Map<Integer, Integer> idLevelNumMap = new HashMap<Integer, Integer>();
            for (WxQuestionCell wxQuestionCell : wxQuestionCells) {
                wxQuestionCell.setLevel_num(level_num);
                idLevelNumMap.put(wxQuestionCell.getId(), level_num);
                level_num ++;
                if (publisherIdNameMap.containsKey(wxQuestionCell.getPublisher_id())) {
                    wxQuestionCell.setPublisher_name(publisherIdNameMap.get(wxQuestionCell.getPublisher_id()));
                } else {
                    String name = "";
                    if (wxQuestionCell.getPublisher_id() == user_id) {
                        name = "我";
                    } else if (wxQuestionCell.getPublisher_id() == teacher_id) {
                        name = "老师";
                    } else if (wxQuestionCell.getPublisher_id() == asker_id) {
                        name = "提问者";
                    } else {
                        char num = (char) pubName ++;
                        name = "用户" + num;
                    }
                    wxQuestionCell.setPublisher_name(name);
                    publisherIdNameMap.put(wxQuestionCell.getPublisher_id(), name);
                }
            }


            // 添加target_level_num target_user_name
            for (WxQuestionCell wxQuestionCell : wxQuestionCells) {
                int parent_id = wxQuestionCell.getParent_id();
                if (parent_id != -1) {
                    int parent_index = idLevelNumMap.get(parent_id);
                    wxQuestionCell.setTarget_level_num(wxQuestionCells.get(parent_index).getLevel_num());
                    wxQuestionCell.setTarget_user_name(wxQuestionCells.get(parent_index).getPublisher_name());
                } else {
                    wxQuestionCell.setTarget_level_num(-1);

                }
            }
        }
        return wxQuestionCells;
    }


//    public List<WxQuestionCell> transWxQUestionCell2NestWxQuestionCell(List<WxQuestionCell> wxQuestionCells) {
//        List<WxQuestionCell> newCells = new LinkedList<WxQuestionCell>();
//        List<WxQuestionCell> temp = new LinkedList<WxQuestionCell>();
//
//        for (WxQuestionCell wxQuestionCell : wxQuestionCells) {
//            if (wxQuestionCell.getParent_id() == -1) {
//                newCells.add(wxQuestionCell);
//            } else {
//                temp.add(wxQuestionCell);
//            }
//        }
//
//        while (temp.size() != 0) {
//            List<WxQuestionCell> temp2 = new LinkedList<WxQuestionCell>();
//            for (int i = temp.size()-1; i >= 0; i--) {
//                Map<String, Object> map = putQuestionToRightPlace(temp.get(i), newCells);
//                if ((Boolean) map.get("result")) {
//                    newCells = (List<WxQuestionCell>) map.get("cells");
//                } else {
//                    temp2.add(temp.get(i));
//                }
//            }
//            temp = temp2;
//        }
//        return newCells;
//    }
//
//    public Map<String, Object> putQuestionToRightPlace(WxQuestionCell temp, List<WxQuestionCell> wxQuestionCells) {
//        Map<String, Object> map = new HashMap<String, Object>();
//        for (WxQuestionCell wxQuestionCell : wxQuestionCells) {
//            if (temp.getParent_id() == wxQuestionCell.getId()) {
//                wxQuestionCell.wxQuestionCells.add(temp);
//                map.put("result", true);
//                map.put("cells", wxQuestionCells);
//                return map;
//            }
//            if (wxQuestionCell.wxQuestionCells.size() != 0) {
//                Map<String, Object> tempMap = putQuestionToRightPlace(temp, wxQuestionCell.wxQuestionCells);
//                if ((Boolean) tempMap.get("result")) {
//                    wxQuestionCell.wxQuestionCells = (List<WxQuestionCell>) tempMap.get("cells");
//                    map.put("result", true);
//                    map.put("cells", wxQuestionCells);
//                    return map;
//                }
//            }
//        }
//        map.put("result", false);
//        return map;
//    }

}
