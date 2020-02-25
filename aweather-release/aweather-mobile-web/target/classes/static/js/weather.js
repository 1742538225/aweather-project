var padaDate = function (value) {    //格式化两位数
    return value < 10 ? '0' + value : value;
};

var weather_vm = new Vue({
    el: "#weatherDiv",
    data: {
        //(weather, 0, 0) in weatherArray
        weatherArray: [],
        hoursArray: [],
        cityZh: "",
        weather: {
            day: "",
            date: "",
            week: "",
            wea: "",
            wea_img: "",
            air: "",
            humidity: "",
            air_level: "",
            air_tips: "",
            tem1: "",
            tem2: "",
            tem: "",
            win_speed: ""
        },
        wea_img: "",
        date: ""
    },
    methods: {
        getCityIdFromCookie() {
            axios({
                method: "get",
                url: "/mobile/city/getCityIdFromCookie.action"
            }).then((response) => {
                this.getWeatherByCityId(response.data);
            }).catch((error) => {
                console.log(error);
            })
        },
        getWeatherByCityId(cityId) {
            axios({
                method: "get",
                params: {id: cityId},
                url: "/mobile/weather/getWeatherByCityId.action"
            }).then((response) => {
                var weatherJson = response.data[0];
                var hoursJson = response.data[1];
                var cityJson = response.data[2];

                this.weatherArray = JSON.parse(weatherJson);
                this.hoursArray = JSON.parse(hoursJson);
                this.cityZh = cityJson;
                this.wea_img = "/mobile/img/" + this.weatherArray[0].wea_img + ".png";
                $("#refreshButton").removeAttr("disabled");
                $("#loadingDiv").css("display","none");
            }).catch((error) => {
                console.log(error);
            });
        },
        getImgUrl(wea_img) {
            return "/mobile/img/" + wea_img + ".png";
        },
        refreshWeather() {
            $("#refreshButton").attr("disabled","disabled");
            $("#loadingDiv").css("display","block");
            this.getCityIdFromCookie();
        }
    }, created() {
        this.getCityIdFromCookie();
    }, filters: {
        //设置一个函数来进行过滤
        formaDate: function (value) {
            //创建一个时间日期对象
            var date = new Date();
            var year = date.getFullYear();      //存储年
            var month = padaDate(date.getMonth() + 1);    //存储月份
            var day = padaDate(date.getDate());         //存储日期
            var hours = padaDate(date.getHours());      //存储时
            var minutes = padaDate(date.getMinutes());  //存储分
            var seconds = padaDate(date.getSeconds());  //存储秒
            //返回格式化后的日期
            return year + '年' + month + '月' + day + '日' + hours + '时' + minutes + '分' + seconds + '秒';
        }
    },
    mounted: function () {
        //创建定时器更新时间
        var _this = this;
        this.timeId = setInterval(function () {
            _this.date = new Date();
        }, 1000);
    },
    beforeDestroy: function () {
        //实例销毁前青出于定时器
        if (this.timeId) {
            clearInterval(this.timeId);
        }
    }
});

