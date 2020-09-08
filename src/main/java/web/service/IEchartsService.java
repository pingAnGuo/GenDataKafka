package web.service;

import web.vo.Series;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public interface IEchartsService {
    List<Series> getListByTime(String time) throws ClassNotFoundException, SQLException, InstantiationException, ParseException, IllegalAccessException;
}
