package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

public class Converter {
    public static Date StringToDate(String s){

        Date result = null;
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            result  = dateFormat.parse(s);
        }

        catch(ParseException e){
            e.printStackTrace();

        }
        return result ;
    }

    public static String DateToString(Date date) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    public static LocalDateTime SqlDateToLocalDateTime(java.sql.Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static String LocalDateTimeToString(LocalDateTime date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    public static Date LocalDateTimeToDate(LocalDateTime date) {
        return Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static java.sql.Date LocalDateTimeToSqlDate(LocalDateTime date) {
        return java.sql.Date.valueOf(LocalDateTimeToString(date));
    }

    public static String SqlTimestampToString(java.sql.Timestamp date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    public static String MapToSqlFindString(Map<String, Object> params, String table){
        StringBuilder sql = new StringBuilder("SELECT * FROM " + table +" WHERE ");
        for(Map.Entry<String, Object> entry : params.entrySet()) {
            Object value = entry.getValue();
            if(value instanceof String) {
                sql.append(entry.getKey()).append("='").append(value).append("' AND ");
            } else
            if(value instanceof Number) {
                sql.append(entry.getKey()).append("=").append(value).append(" AND ");
            } else
            if(value instanceof LocalDateTime) {
                value = Converter.LocalDateTimeToString((LocalDateTime)value);
                sql.append(entry.getKey()).append("='").append(value).append("' AND ");
            }
        }
        sql.delete(sql.length() - 4, sql.length());
        return sql.toString();
    }
}
