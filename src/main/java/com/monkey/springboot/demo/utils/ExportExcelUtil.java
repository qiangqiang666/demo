package com.monkey.springboot.demo.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 描述:
 * 〈导出excel文件工具类〉
 *
 * @author Monkey
 * @create 2019/6/2 15:23
 */
public class ExportExcelUtil {

    public static void outPutToBigExcel(List list, String reportName,String[] columnNames ,HttpServletResponse response) {
        if (null != list && list.size() > 0) {
            long startTime = System.currentTimeMillis();
            String fileName = new SimpleDateFormat("yyyy/MM/dd").format(new Date()) + ".xlsx";
            SXSSFWorkbook wb = new SXSSFWorkbook(100);
            Sheet sheet = null; // 工作表对象
            Row nRow = null; // 行对象
            Cell nCell = null; // 列对象
            try {
                int rowNo = 0; // 总行号
                int pageRowNo = 0; // 页行号
                //遍历map中的值
                for (Object obj : list) {
                    // 打印300000条后切换到下个工作表，可根据需要自行拓展，2百万，3百万...数据一样操作，只要不超过1048576就可以
                    if (rowNo % 300000 == 0) {
                        //System.out.println("当前sheet页为:" + rowNo / 300000);
                        sheet = wb.createSheet("第" + (rowNo / 300000 + 1) + "页"+reportName+"报表");// 建立新的sheet对象
                        sheet = wb.getSheetAt(rowNo / 300000); // 动态指定当前的工作表
                        pageRowNo = 1; // 每当新建了工作表就将当前工作表的行号重置为1

                        //定义表头
                        nRow = sheet.createRow(0);
                        for (int i = 0; i < columnNames.length; i++) {
                            Cell cel0 = nRow.createCell(i);
                            cel0.setCellValue(columnNames[i]);
                        }
                    }
                    rowNo++;
                    nRow = sheet.createRow(pageRowNo++); // 新建行对象

                    //得到class
                    Class cls = obj.getClass();
                    //得到所有属性
                    Field[] fields = cls.getDeclaredFields();
                    for (int i = 0; i < fields.length; i++) {//遍历
                        //得到属性
                        Field field = fields[i];
                        //打开私有访问
                        field.setAccessible(true);
                        //获取属性值
                        Object value = field.get(obj);
                        Cell cel0 = nRow.createCell(i);
                        cel0.setCellValue(value.toString());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            OutputStream os = null;
            try {
                fileName = new String(fileName.getBytes(), "UTF-8");
                response.setContentType("application/octet-stream;charset=UTF-8");
                response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
                response.addHeader("Pargam", "no-cache");
                response.addHeader("Cache-Control", "no-cache");
                fileName = new String(fileName.getBytes(), "ISO8859-1");
                response.setContentType("application/octet-stream;charset=ISO8859-1");
                response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
                response.addHeader("Pargam", "no-cache");
                response.addHeader("Cache-Control", "no-cache");
                os = response.getOutputStream();
                wb.write(os);
                os.flush();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            long stopTime = System.currentTimeMillis(); // 写文件时间
            System.out.println("数据写入Excel表格中耗时 : " + (stopTime - startTime) + "ms");
        }
    }
}