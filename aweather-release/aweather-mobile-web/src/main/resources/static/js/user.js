var vm_user = new Vue({
    el: "#userDiv",
    data: {
        user: {
            id: "",
            name: "请先登录",
            email: "请先登录",
            vip_start: "",
            vip_stop: "",
            created: "请先登录",
            updated: ""
        },
        vip_time: "",
        notVip:true,
        isVip:false
    },
    methods: {
        loadUser() {
            axios({
                method: "get",
                url: "/mobile/user/getUserMessage.action"
            }).then((response) => {
                    if (response.data != "") {
                        this.user = response.data;
                        var vip_stop = response.data.vip_stop;
                        if (vip_stop == null) {
                            this.notVip = true;
                            this.isVip = false;
                            this.vip_time = "未开通会员或已到期";
                        } else {
                            var vipTime = getTimeSpace((new Date()).getTime(), vip_stop);
                            if (vipTime[0] > 0) {
                                this.notVip = false;
                                this.isVip = true;
                                this.vip_time = "剩余" + vipTime[0].toFixed(1) + "天";
                            }else{
                                this.notVip = true;
                                this.isVip = false;
                                this.vip_time = "未开通会员或已到期";
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
        this.loadUser();
    },
    filters: {
        formatDate: function (value) {
            if (value=="请先登录"){
                return null;
            }
            let date = new Date(value);
            let y = date.getFullYear();
            let MM = date.getMonth() + 1;
            MM = MM < 10 ? ('0' + MM) : MM;
            let d = date.getDate();
            d = d < 10 ? ('0' + d) : d;
            let h = date.getHours();
            h = h < 10 ? ('0' + h) : h;
            let m = date.getMinutes();
            m = m < 10 ? ('0' + m) : m;
            let s = date.getSeconds();
            s = s < 10 ? ('0' + s) : s;
            return y + '-' + MM + '-' + d + ' ' + h + ':' + m + ':' + s;
        }
    }
});