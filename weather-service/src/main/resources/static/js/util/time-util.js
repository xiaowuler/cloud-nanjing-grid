var TimeUtil = function () {

    this.GetWeekStartTime = function () {
        return moment().weekday(1).format('YYYY/MM/DD');
    };

    this.GetWeekEndTime = function(){
        return moment().weekday(7).format('YYYY/MM/DD');
    };

    this.GetMonthStartTime = function(){
        return moment().startOf('month').format('YYYY/MM/DD');
    };

    this.GetMonthEndTime = function(){
        return moment().endOf('month').format('YYYY/MM/DD');
    };

    this.GetYearStartTime = function(){
        return moment().year(moment().year()).startOf('year').format('YYYY/MM/DD');
    };

    this.GetYearEndTime = function(){
        return moment().year(moment().year()).endOf('year').format('YYYY/MM/DD');
    };
};