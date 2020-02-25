/**
 * 计算时间戳之间的差值
 *
 * @param start_time 开始时间戳
 * @param end_time 结束时间戳
 */

function calculateDiffTime(start_time, end_time) {
    var startTime = 0, endTime = 0
    if (start_time < end_time) {
        startTime = start_time
        endTime = end_time
    } else {
        startTime = end_time
        endTime = start_time
    }

//计算天数
    var timeDiff = endTime - startTime
    var year = Math.floor(timeDiff / 86400 / 365);
    timeDiff = timeDiff % (86400 * 365);
    var month = Math.floor(timeDiff / 86400 / 30);
    timeDiff = timeDiff % (86400 * 30);
    var day = Math.floor(timeDiff / 86400);
    timeDiff = timeDiff % 86400;
    var hour = Math.floor(timeDiff / 3600);
    timeDiff = timeDiff % 3600;
    var minute = Math.floor(timeDiff / 60);
    timeDiff = timeDiff % 60;
    var second = timeDiff;
    return [year, month, day, hour, minute, second]

    // var timeDiff = endTime - startTime
    // var hour = Math.floor(timeDiff / 3600);
    // timeDiff = timeDiff % 3600;
    // var minute = Math.floor(timeDiff / 60);
    // timeDiff = timeDiff % 60;
    // var second = timeDiff;
    // return [hour, minute, second]
}

function getTimeSpace(start,end){
    var utc=end-start;

    var day = utc/(24*60*60*1000);// 天

    var hour = utc/(60*60*1000);//小时

    var minute = utc/(60*1000); // 分

    return [day,hour,minute];
}