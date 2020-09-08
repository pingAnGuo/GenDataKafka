package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import web.Util.DBUtil;
import web.service.IEchartsService;
import web.vo.Series;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("/Echarts")
public class EChartsCotroller {
    @Autowired
    private IEchartsService service;

//    private DBUtil dbUtil;
    @RequestMapping(value = "/getdata",method = RequestMethod.POST)
    public  @ResponseBody List<Series> doPost(String js_time) throws SQLException, ParseException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        System.out.println("===="+js_time);
        List<Series> list = service.getListByTime(js_time);
        System.out.println(list.size());
        for(int i = 0;i<list.size();i++){
            System.out.println("+++++++"+list.get(i).x_time+"-------"+list.get(i).y_count);
        }
        return list;
    }
}
