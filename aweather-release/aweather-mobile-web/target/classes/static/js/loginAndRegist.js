var loginAndRegist_vm = new Vue({
        el: "#loginAndRegistModal",
        data: {
            user: {
                id: "",
                name: "",
                email: "",
                password: ""
            },
            comName: "login"
        },
        methods:
            {
                toRegist() {        //切换为注册页
                    $("#nameInput").css("display", "");
                    $("#confirmPswInput").css("display", "");
                    $("#doRegistInput").css("display", "inline");
                    $("#newUserInput").css("display", "none");
                    $("#remember").css("display", "none");
                    $("#doLoginInput").addClass("glyphicon glyphicon-chevron-left");
                    $("#doLoginInput").text("返回登录");
                    $("#title").text("欢迎加入Aweather");
                    $("#QQDiv").css("display", "none");
                    this.clearForm();
                }
                ,
                toLogin() {     //切换为登录页
                    if ($("#doLoginInput").text() != "登录") {    //返回登录页
                        $("#nameInput").css("display", "none");
                        $("#confirmPswInput").css("display", "none");
                        $("#doRegistInput").css("display", "none");
                        $("#newUserInput").css("display", "inline");
                        $("#remember").css("display", "inline");
                        $("#doLoginInput").removeClass("glyphicon glyphicon-chevron-left");
                        $("#doLoginInput").text("登录");
                        $("#title").text("登录Aweather");
                        $("#QQDiv").css("display", "inline");
                        this.clearForm();
                    } else {
                        this.doLogin();
                        vm_user.loadUser();
                    }
                }
                ,
                clearForm() {
                    this.user.id = '';
                    this.user.name = '';
                    this.user.password = '';
                    this.user.email = '';
                    $("#confirmPasswordInput").val("");
                    $("#loginAndRegistSpan").html("");
                }
                ,
                doRegist() {
                    if (this.user.password != $("#confirmPasswordInput").val()) {
                        $("#loginAndRegistSpan").html("<font class='glyphicon glyphicon-remove' color='red'>两次输入的密码不一致!</font>");
                    } else {
                        axios({
                            method: "post",
                            data: this.user,
                            url: "/mobile/user/doRegist.action"
                        }).then((response) => {
                            if (response.data == 1) {   //可以注册
                                this.clearForm();
                                this.toLogin();
                                $("#loginAndRegistSpan").html('<font class=\'glyphicon glyphicon-ok\' color=\'green\'>注册成功!</font>');
                            } else {     //无法注册,data返回错误信息  2邮箱为空 3密码为空 4邮箱格式错误 5邮箱已被注册 6未知错误
                                var msg = "";
                                if (response.data == 2) {
                                    msg = "邮箱不能为空!";
                                }
                                if (response.data == 3) {
                                    msg = "密码不能为空!";
                                }
                                if (response.data == 4) {
                                    msg = "邮箱格式有误!";
                                }
                                if (response.data == 5) {
                                    msg = "该邮箱已被注册!";
                                }
                                if (response.data == 6) {
                                    msg = "遇到未知错误,请稍后重试!";
                                }
                                $("#loginAndRegistSpan").html("<font class='glyphicon glyphicon-remove' color='red'>" + msg + "</font>");
                            }
                        }).catch((error) => {
                            console.log(error);
                        });
                    }
                }
                ,
                doLogin() {
                    if (this.user.email == null || this.user.email == "") {
                        alert("请输入邮箱!");
                    }
                    else if (this.user.password == null || this.user.password == "") {
                        alert("请输入密码!");
                    } else {
                        axios({
                            method: "post",
                            url: "/mobile/user/doLogin.action",
                            data: this.user
                        }).then((response) => {
                            var code = response.data;
                            if (code == 1) {    //登陆成功
                                $("#loginAndRegistModal").modal("hide");
                                $("#alertDiv").removeClass("alert-danger");
                                $("#alertDiv").addClass("alert-success");
                                $("#alertDiv").css("display", "block");
                                $("#alertSpan").html("欢迎您!用户" + this.user.email);

                            } else if (code == -1) { //参数错误
                                console.log("请传入正确的用户信息");
                            } else if (code == 2) {  //密码错误
                                alert("请输入正确的密码!");
                            } else if (code == 3) {  //用户名错误
                                alert("请输入正确的邮箱!");
                            } else if (code == 0) {  //服务器错误
                                alert("抱歉!服务器出错,请稍后再试");
                            }
                        }).catch((error) => {
                            console.log(error);
                        });
                    }
                }
            },
        components: {
            login: {
                template: "#login"
            },
            register: {
                template: "#register"
            }
        }
    })
;