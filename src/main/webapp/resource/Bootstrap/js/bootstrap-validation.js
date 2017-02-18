
!function($) {
    $.fn.validation = function(options) {
        return this.each(function() {
            globalOptions = $.extend({}, $.fn.validation.defaults, options);
            validationForm(this)
        });
    };

    $.fn.validation.defaults = {
        validRules : [
            {name: 'required', validate: function(value) {return ($.trim(value) == '');}, defaultMsg: '不能为空'},
            {name: 'number', validate: function(value) {return (!/^[0-9]\d*$/.test(value));}, defaultMsg: '请输入数字。'},
            {name: 'email', validate: function(value) {return (!/^[a-zA-Z0-9]{1}([\._a-zA-Z0-9-]+)(\.[_a-zA-Z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+){1,3}$/.test(value));}, defaultMsg: '请输入邮箱地址。'},
            {name: 'char', validate: function(value) {return (!/^[a-zA-Z]*$/.test(value));}, defaultMsg: '请输入英文字符。'},
            {name: 'qq', validate: function(value) {return (!/^[^0]\d{4,9}$/.test(value));}, defaultMsg: '请输入正确QQ'},
            {name: 'phone', validate: function(value) {return (!/^((\(\d{2,3}\))|(\d{3}\-))?1(3|4|5|6|8)\d{9}$/.test(value));}, defaultMsg: '请输入正确手机号码'},
            {name: 'password', validate: function(value) {return (!safePassword(value));}, defaultMsg: '密码由字母和数字组成，至少6位'},// check-type="password"
            {name: 'rePassword', validate: function(value,param) { return (!(value == $(param).val()));}, defaultMsg: '两次输入的字符不一至'},//  check-type="rePassword #inputPassword"   ,inputPassword需要比较的ID
            {name: 'idcard', validate: function(value) {return (!idCard(value));}, defaultMsg: '身份证号码不正确'},
            {name: 'chinese', validate: function(value) {return (!/^[\u4e00-\u9fff]$/.test(value));}, defaultMsg: '请输入汉字。'}
        ]
    };
    /* 密码由字母和数字组成，至少6位 */
    var safePassword = function (value) {
        return !(/^(([A-Z]*|[a-z]*|\d*|[-_\~!@#\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]*)|.{0,5})$|\s/.test(value));
    }

    /*身份证验证*/
    var idCard = function (value) {
        if (value.length == 18 && 18 != value.length) return false;
        var number = value.toLowerCase();
        var d, sum = 0, v = '10x98765432', w = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2], a = '11,12,13,14,15,21,22,23,31,32,33,34,35,36,37,41,42,43,44,45,46,50,51,52,53,54,61,62,63,64,65,71,81,82,91';
        var re = number.match(/^(\d{2})\d{4}(((\d{2})(\d{2})(\d{2})(\d{3}))|((\d{4})(\d{2})(\d{2})(\d{3}[x\d])))$/);
        if (re == null || a.indexOf(re[1]) < 0) return false;
        if (re[2].length == 9) {
            number = number.substr(0, 6) + '19' + number.substr(6);
            d = ['19' + re[4], re[5], re[6]].join('-');
        } else d = [re[9], re[10], re[11]].join('-');
        if (!isDateTime.call(d, 'yyyy-MM-dd')) return false;
        for (var i = 0; i < 17; i++) sum += number.charAt(i) * w[i];
        return (re[2].length == 9 || number.charAt(17) == v.charAt(sum % 11));
    }

    var isDateTime = function (format, reObj) {
        format = format || 'yyyy-MM-dd';
        var input = this, o = {}, d = new Date();
        var f1 = format.split(/[^a-z]+/gi), f2 = input.split(/\D+/g), f3 = format.split(/[a-z]+/gi), f4 = input.split(/\d+/g);
        var len = f1.length, len1 = f3.length;
        if (len != f2.length || len1 != f4.length) return false;
        for (var i = 0; i < len1; i++) if (f3[i] != f4[i]) return false;
        for (var i = 0; i < len; i++) o[f1[i]] = f2[i];
        o.yyyy = s(o.yyyy, o.yy, d.getFullYear(), 9999, 4);
        o.MM = s(o.MM, o.M, d.getMonth() + 1, 12);
        o.dd = s(o.dd, o.d, d.getDate(), 31);
        o.hh = s(o.hh, o.h, d.getHours(), 24);
        o.mm = s(o.mm, o.m, d.getMinutes());
        o.ss = s(o.ss, o.s, d.getSeconds());
        o.ms = s(o.ms, o.ms, d.getMilliseconds(), 999, 3);
        if (o.yyyy + o.MM + o.dd + o.hh + o.mm + o.ss + o.ms < 0) return false;
        if (o.yyyy < 100) o.yyyy += (o.yyyy > 30 ? 1900 : 2000);
        d = new Date(o.yyyy, o.MM - 1, o.dd, o.hh, o.mm, o.ss, o.ms);
        var reVal = d.getFullYear() == o.yyyy && d.getMonth() + 1 == o.MM && d.getDate() == o.dd && d.getHours() == o.hh && d.getMinutes() == o.mm && d.getSeconds() == o.ss && d.getMilliseconds() == o.ms;
        return reVal && reObj ? d : reVal;
        function s(s1, s2, s3, s4, s5) {
            s4 = s4 || 60, s5 = s5 || 2;
            var reVal = s3;
            if (s1 != undefined && s1 != '' || !isNaN(s1)) reVal = s1 * 1;
            if (s2 != undefined && s2 != '' && !isNaN(s2)) reVal = s2 * 1;
            return (reVal == s1 && s1.length != s5 || reVal > s4) ? -10000 : reVal;
        }
    };





    var formState = false, fieldState = false, wFocus = false, globalOptions = {};

    var validateField = function(field, valid,param) { // 验证字段
        var el = $(field), error = false, errorMsg = '';
        for (var i = 0; i < valid.length; i++) {
            var x = true, flag = valid[i], msg = (el.attr(flag + '-message')==undefined)?null:el.attr(flag + '-message');;
            if (flag.substr(0, 1) == '!') {
                x = false;
                flag = flag.substr(1, flag.length - 1);
            }

            var rules = globalOptions.validRules;
            for (j = 0; j < rules.length; j++) {
                var rule = rules[j];
                if (flag == rule.name) {
                    if (rule.validate.call(field, el.val(),param) == x) {
                        error = true;
                        errorMsg = (msg == null)?rule.defaultMsg:msg;
                        break;
                    }else{
                        error = false;
                        fieldState=true;
                        break;
                    }
                }
            }

            if (error) {break;}
        }

        var controls = el.parents('.controls'), controlGroup = el.parents('.control-group'), errorEl = controls.children('.help-block, .help-inline');

        if (error) {

            if (!controlGroup.hasClass('error')) {
                if (errorEl.length > 0) {
                    var help = errorEl.text();
                    controls.data('help-message', help);
                    errorEl.text(errorMsg);
                } else {
                    controls.append('<span class="help-inline">'+errorMsg+'</span>');
                }
                controlGroup.attr('class','control-group error');
            }
        } else {

            if (fieldState) {
                if (errorEl.length > 0) {
                    var help = controls.data('help-message');
                    if (help == undefined) {
                        errorEl.remove();
                    } else {
                        errorEl.text(help);
                    }
                }


                controlGroup.attr('class','control-group success');
            } else {
                if (errorEl.length > 0) {
                    var help = errorEl.text();
                    controls.data('help-message', help);
                }
            }
        }
        return !error;
    };

    var validationForm = function(obj) { // 表单验证方法
        $(obj).submit(function() { // 提交时验证
            if (formState) { // 重复提交则返回
                return false;
            }
            formState = true;
            var validationError = false;
            $('input, textarea', this).each(function () {
                var el = $(this), valids = (el.attr('check-type')==undefined)?null:el.attr('check-type').split(' ');

                if(valids.length==2)
                {
                    valid=valids[0].split(' ');
                    param=valids[1];

                } else{
                    param=undefined;
                    valid=valids;

                }
                if (valid != null && valid.length > 0) {
                    if (!validateField(this, valid,param)) {
                        if (wFocus == false) {
                            scrollTo(0, el[0].offsetTop - 50);
                            wFocus = true;
                        }

                        validationError = true;
                    }
                }
            });

            wFocus = false;
            fieldState = true;

            if (validationError) {
                formState = false;

                $('input, textarea').each(function() {
                    var el = $(this), valid = (el.attr('check-type')==undefined)?null:el.attr('check-type').split(' ');
                    if (valid != null && valid.length > 0) {
                        el.focus(function() { // 获取焦点时
                            var controls = el.parents('.controls'), controlGroup = el.parents('.control-group'), errorEl = controls.children('.help-block, .help-inline');
                            if (errorEl.length > 0) {
                                var help = controls.data('help-message');
                                if (help == undefined) {
                                    errorEl.remove();
                                } else {
                                    errorEl.text(help);
                                }
                            }
                            controlGroup.attr('class','control-group');
                        });

                        el.blur(function() { // 失去焦点时
                            var el = $(this), valids = (el.attr('check-type')==undefined)?null:el.attr('check-type').split(' ');

                            if(valids.length==2)
                            {
                                valid=valids[0].split(' ');
                                param=valids[1];

                            } else{
                                param=undefined;
                                valid=valids;

                            }
                            validateField(this, valid,param);
                        });
                    }
                });

                return false;
            }

            return true;
        });


    };
}(window.jQuery);
