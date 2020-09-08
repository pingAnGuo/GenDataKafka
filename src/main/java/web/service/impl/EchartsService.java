package web.service.impl;

import org.springframework.stereotype.Service;
import web.Util.DBUtil;
import web.service.IEchartsService;
import web.vo.Series;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

@Service("echartsService")
public class EchartsService implements IEchartsService {
    @Override
    public List<Series> getListByTime(String time) throws ClassNotFoundException, SQLException, InstantiationException, ParseException, IllegalAccessException {
        DBUtil util = new DBUtil();
        return util.getFromDBByTime(time);
    }


}
