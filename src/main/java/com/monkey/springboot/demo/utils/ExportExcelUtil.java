package com.monkey.springboot.demo.utils;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
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
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 描述:
 * 〈导出excel文件工具类〉
 *
 * @author Monkey
 * @create 2019/6/2 15:23
 */
public class ExportExcelUtil {

    /**
     * 快速导出大量数据excel
     * ps: 只针对list泛型为对象,不能为包装类型(String,Double,Integer等等...)
     *
     * @param list
     * @param reportName
     * @param columnNames
     * @param response
     */
    public static void outPutToBigExcel(List list, String reportName, LinkedHashMap<String, String> columnNames, HttpServletResponse response) {
        //long startTime = System.currentTimeMillis();
        String fileName = new SimpleDateFormat("yyyy/MM/dd").format(new Date()) + ".xlsx";
        SXSSFWorkbook wb = new SXSSFWorkbook(100);
        // 工作表对象
        Sheet sheet = null;
        // 行对象
        Row nRow = null;
        // 列对象
        Cell nCell = null;

        // 设置值的索引坐标
        LinkedHashMap<String, Integer> indexMap = Maps.newLinkedHashMap();
        try {
            // 总行号
            int rowNo = 0;
            // 页行号
            int pageRowNo = 0;
            // 遍历map
            if (null == list || list.size() <= 0) {
                /**
                 * 如果为空,则直接返回空文档..
                 */
                sheet = wb.createSheet("第1页" + reportName + "报表");
                sheet = wb.getSheetAt(0);
                pageRowNo = 1;
                nRow = sheet.createRow(0);
                int i = 0;
                for (String key : columnNames.keySet()) {
                    Cell cel0 = nRow.createCell(i);
                    cel0.setCellValue(columnNames.get(key));
                    i++;
                }
                rowNo++;
                nRow = sheet.createRow(pageRowNo++);
            } else {
                //Byte 、Short、Integer、Long、Float、Double、Character、Boolean、String
                if (TypeUtil.IsPackingType(list.get(0))){
                    throw new RuntimeException("Data is not allowed as a wrapper type");
                }
                for (Object obj : list) {
                    // 打印300000条后切换到下个工作表，可根据需要自行拓展，2百万，3百万...数据一样操作，只要不超过1048576就可以
                    if (rowNo % 300000 == 0) {
                        //System.out.println("当前sheet页为:" + rowNo / 300000);
                        // 建立新的sheet对象
                        sheet = wb.createSheet("第" + (rowNo / 300000 + 1) + "页" + reportName + "报表");
                        // 动态指定当前的工作表
                        sheet = wb.getSheetAt(rowNo / 300000);
                        // 每当新建了工作表就将当前工作表的行号重置为1
                        pageRowNo = 1;

                        //定义表头
                        nRow = sheet.createRow(0);
                        int i = 0;
                        for (String key : columnNames.keySet()) {
                            Cell cel0 = nRow.createCell(i);
                            cel0.setCellValue(columnNames.get(key));
                            indexMap.put(key, i);
                            i++;
                        }
                    }
                    rowNo++;
                    // 新建行对象
                    nRow = sheet.createRow(pageRowNo++);

                    //得到class
                    Class cls = obj.getClass();
                    //得到所有属性
                    Field[] fields = cls.getDeclaredFields();
                    //遍历
                    for (int index = 0; index < fields.length; index++) {
                        //得到属性
                        Field field = fields[index];
                        //打开私有访问
                        field.setAccessible(true);
                        //获取属性名
                        String name = field.getName();
                        String mapValue = columnNames.get(name);
                        if (StringUtils.isNotEmpty(mapValue)) {
                            //获取属性值
                            Object value = field.get(obj);
                            // 根据坐标,设置值
                            Cell cel0 = nRow.createCell(indexMap.get(name));
                            if (null == value) {
                                cel0.setCellValue("");
                            } else {
                                if (value instanceof Enum) {
                                    cel0.setCellValue(((Enum) value).name());
                                } else if (value instanceof Date) {
                                    cel0.setCellValue(DateFormatUtil.dateToString((Date) value));
                                } else if (value instanceof List) {
                                    List valueList = (List) value;
                                    cel0.setCellValue(valueList.size());
                                } else {
                                    cel0.setCellValue(value.toString());
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //导出数据
        responseExcel(wb, fileName, response);
        //long stopTime = System.currentTimeMillis(); // 写文件时间
        //System.out.println("|【主服务】|数据写入Excel表格中耗时 : " + (stopTime - startTime) + "ms");
    }

    /**
     * 导出数据
     *
     * @param wb
     * @param fileName
     * @param response
     */
    private static void responseExcel(SXSSFWorkbook wb, String fileName, HttpServletResponse response) {
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
    }
}