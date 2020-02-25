var pay_vm = new Vue({
    el: "#payDiv",
    data: {
        user: {
            email: "",
            name: "",
            vip_stop: ""
        },
        paymentObject: {
            id: "",
            name: "",
            price: "",
            description: ""
        },
        paymentObjectArray: [],
        payToken: "",
        vip_time: "0"
    },
    methods: {
        async toPayForVip(price) {
            var flag = true;    //标记是否登录
            await axios({       //同步提交,避免表单元素的值未修改就提交表单
                method: "post",
                url: "/mobile/pay/toPayForVip.action",
                data: price.toString()
            }).then((response) => {
                if (response.data == "202") {   //用户登录信息过期
                    flag = false;
                } else {     //返回订单token
                    this.payToken = response.data;
                }
            }).catch((error) => {
                console.log(error);
            });
            if (flag == true) {
                $("#payForm").submit();
            } else {
                alert("请先登录!");
            }
        },
        getVipItem() {
            axios({
                method: "get",
                url: "/mobile/pay/getAllVipItem.action"
            }).then((response) => {
                this.paymentObjectArray = response.data;
            }).catch((error) => {
                console.log(error);
            });
        },
        loadUser() {
            axios({
                method: "get",
                url: "/mobile/user/getUserMessage.action"
            }).then((response) => {
                    if (response.data != "") {
                        this.user = response.data;
                        var vip_stop = response.data.vip_stop;
                        if (vip_stop == null) {
                            this.vip_time = "0";
                        } else {
                            var vipTime = getTimeSpace((new Date()).getTime(), vip_stop);
                            if (vipTime[0] > 0) {
                                this.vip_time = vipTime[0].toFixed(1);
                            } else {
                                this.vip_time = "0";
                            }
                        }
                    }
                }
            ).catch((error) => {
                console.log(error);
            });
        }
    },
    created() {
        this.getVipItem();
        this.loadUser();
    }
});