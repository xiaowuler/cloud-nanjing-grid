var timeUtil = function () {

    this.getThisWeekStartTime = function () {
        return moment().weekday(1).format('YYYY/MM/DD');
    }

    this.getThisWeekEndTime = function(){
        return moment().weekday(7).format('YYYY/MM/DD');
    }

}