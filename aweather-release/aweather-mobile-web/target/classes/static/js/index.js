var index_vm = new Vue({
    el: "#indexDiv",
    data: {
        provinceArrray: [],
        selectProvince: '-选择省份-',
        selectCity: '-选择地区-',
        cityArray: [],
        location: '定位中...',
        ifLogin:false,
        user:{
            email:"",
            name:""
        }
    },
    methods: {
        getAllProvince() {      //获取所有省份
            axios({
                method: "get",
                url: "/mobile/city/getAllProvince.action"
            }).then((response) => {
                this.provinceArrray = response.data;
            }).catch((error) => {
                console.log(error);
            });
        },
        getCityByProvince() {       //根据省份名称获取所有地区名称
            this.selectCity="-选择地区-";
            axios({
                method: "get",
                params: {
                    province: this.selectProvince
                },
                url: "/mobile/city/getCityByProvince.action"
            }).then((response) => {
                this.cityArray = response.data
            }).catch((error) => {
                console.log(error);
            });
        },
        getLocation() {      //显示顶部定位数据,随着所选地区改变
            if (this.selectCity != '-选择地区-') {
                this.location = this.selectCity;
                this.setCityIdToCookie(this.location);
            }
        },
        getCity() {    //定位
            $("#locationLoading").css("display","block");
            $("#locationButton").attr("disabled","disabled");
            this.removeCityIdFromCookie();       //定位不需要取cookie里的城市id,所以先清除
            this.selectCity = '-选择地区-';
            this.selectProvince = '-选择省份-';
            this.location = '定位中...';
            const geolocation = new BMap.Geolocation();
            geolocation.getCurrentPosition((position)=> {
                let city = position.address.city;             //获取城市信息
                let province = position.address.province;    //获取省份信息
                city = city.replace("市","");
                city = city.replace("区","");
                city = city.replace("县","");
                this.setCityIdToCookie(city);
                this.location = city;
                $("#locationButton").removeAttr("disabled");
                // $("#locationLoading").css("display","none");
            }, (error)=>{
                // $("#locationLoading").css("display","none");
                this.location = "定位失败";
                alert("请确定是否开启了定位,或者手动选择位置");
            }, {provider: 'baidu'});
        },
        setCityIdToCookie(city){
            axios({
               method: "get",
               url:"/mobile/city/setCityIdToCookie.action",
               params:{cityZh:city}
            }).catch((error)=>{
                console.log(error);
            });
        },
        removeCityIdFromCookie(){
            axios({
                method: "get",
                url:"/mobile/city/removeCityIdFromCookie.action"
            }).catch((error)=>{
                console.log(error);
            })
        }
    }, created() {
        this.getAllProvince();
        this.getCity();
    }
});
