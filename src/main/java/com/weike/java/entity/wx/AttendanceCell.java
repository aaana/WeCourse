package com.weike.java.entity.wx;

/**
 * Created by tina on 4/4/17.
 */
public class AttendanceCell {
    private int stu_num;
    private int attendance;
    private int absence;
    private int attendance_rate;

    public AttendanceCell() {
    }

    public AttendanceCell(int stu_num, int attendance, int absence, int attendance_rate) {
        this.stu_num = stu_num;
        this.attendance = attendance;
        this.absence = absence;
        this.attendance_rate = attendance_rate;
    }

    public int getStu_num() {
        return stu_num;
    }

    public void setStu_num(int stu_num) {
        this.stu_num = stu_num;
    }

    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

    public int getAbsence() {
        return absence;
    }

    public void setAbsence(int absence) {
        this.absence = absence;
    }

    public int getAttendance_rate() {
        return attendance_rate;
    }

    public void setAttendance_rate(int attendance_rate) {
        this.attendance_rate = attendance_rate;
    }
}
