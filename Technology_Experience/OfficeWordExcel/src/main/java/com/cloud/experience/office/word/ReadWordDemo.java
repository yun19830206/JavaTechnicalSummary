package com.cloud.experience.office.word;



import fr.opensagres.poi.xwpf.converter.core.BasicURIResolver;
import fr.opensagres.poi.xwpf.converter.core.FileImageExtractor;
import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLConverter;
import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.util.List;


/**
 * Java读取Word相关示例
 * Created by ChengYun on 2019/3/5 Version 1.0
 */
public class ReadWordDemo {

    public static void main(String[] args) {

        ReadWordDemo readWordDemo = new ReadWordDemo();

        InputStream inputStream = null ;
        XWPFDocument wordDocument = null ;
        try {
            //1：打开Word文件
            inputStream = new FileInputStream("d:/office/Office.docx");
            wordDocument = new XWPFDocument(inputStream);

            //2：读取段落全部内容并展示
            readWordDemo.printWordSegment(wordDocument);
            System.out.println("*************************************************************************");

            //3：读取文档目录结构并打印
            readWordDemo.printWordStructure(wordDocument);
            System.out.println("*************************************************************************");

            //4：读取文档中表格并打印
            readWordDemo.printWordTable(wordDocument);
            System.out.println("*************************************************************************");

            //5：读取文档中图片
            readWordDemo.printWordPicture(wordDocument);
            System.out.println("*************************************************************************");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(null != wordDocument){
                    wordDocument.close();
                }
                if(null != inputStream){
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ReadWordDemo.transWord2Html();
    }

    /**
     * 读取文档中图片<br/>
     * @param wordDocument
     */
    private void printWordPicture(XWPFDocument wordDocument) {
        for(XWPFPictureData picture : wordDocument.getAllPictures()){
            System.out.println("文档中第一个图片信息如下");
            System.out.println("    图片总大小="+picture.getChecksum());
            System.out.println("    图片直接数据="+picture.getData());
            System.out.println("    图片文件名="+picture.getFileName());
            System.out.println("    图片类型="+picture.getPictureType());
        }

    }

    /**
     * 读取文档中表格并打印<br/>
     *   1：通过XWPFDocument.getTables()可以获得Word中的所有表格
     *   2：通过XWPFTable.getRows()可以获得表格的所有行数据
     *   3：通过XWPFTableRow.getTableCells()可以获得表格一行的所有列单元格数据
     *   4：通过XWPFTableCell.getText()可以获得单元格的内容
     * 数据读取特征<br/>
     *   1：如果有一列被拆分两行单元格，导致的结果是 整个表格 的行数 多一行，数据都在第一行，拆分出来的在第二行的某个单元格
     *   2：如果有一列被拆分两列单元格，则本行的单元格数量会比以前多一个
     *   3：合并单元格列的，合并后的结果只会在第一行的本列有有值，其他行没有值。
     * @param wordDocument
     */
    private void printWordTable(XWPFDocument wordDocument) {
        List<XWPFTable> tables = wordDocument.getTables();
        for(XWPFTable table : tables){
            //table.getText() 获得整个表格的全部内容，不带边框和样式
            System.out.println("====================================");
            for(XWPFTableRow row : table.getRows()){
                System.out.print(row.getTableCells().size());
                for(XWPFTableCell tableCell : row.getTableCells()){
                    System.out.print("    ||    "+tableCell.getText());
                }
                System.out.println("    ||");
            }
        }
    }

    /**
     * 读取文件目录结构并打印,特征如下<br/>
     *  段落级别10=一级标题; 段落级别2=二级标题;  段落级别4=三级标题;
     * @param wordDocument
     */
    private void printWordStructure(XWPFDocument wordDocument) {
        //获取全部段落List
        List<XWPFParagraph> paras = wordDocument.getParagraphs();
        for (XWPFParagraph para : paras) {
            //段落级别10=一级标题,不打印前导格内容
            if("10".equals(para.getStyleID())){
                System.out.println(para.getParagraphText());
            }
            //段落级别2=二级标题,打印两个前导格
            if("2".equals(para.getStyleID())){
                System.out.println("  "+para.getParagraphText());
            }
            //段落级别4=二级标题,打印事个前导格
            if("4".equals(para.getStyleID())){
                System.out.println("    "+para.getParagraphText());
            }
        }
    }

    /**
     * 读取Word段落内容并展示，不能读取Word中的Excel表格与图片,特征如下<br/>
         段落级别null
         段落内容:
       =======》代表空行
         段落级别null
         段落内容:跟读doc文件一样，POI在读docx文件的时候也有两种方式。。。
       =======》代表读到了一个正文内容
         段落级别4
         段落内容:桑切斯进球2进球前
       =======》代表读到了一个目录内容
         段落级别10=一级标题; 段落级别2=二级标题;  段落级别4=三级标题;
     * @param wordDocument
     */
    private void printWordSegment(XWPFDocument wordDocument) {
        //获取全部段落List
        List<XWPFParagraph> paras = wordDocument.getParagraphs();
        for (XWPFParagraph para : paras) {
            //得到xml格式全部内容
            //System.out.println(para.getCTP());
            //段落级别
            System.out.println("段落级别" + para.getStyleID());
            //段落内容
            System.out.println("段落内容:" + para.getParagraphText());
            //段落内容
            System.out.println("===================================");
        }
    }

    /**
     * 将一个Word文档转换成Html
     */
    private static void transWord2Html() {
        String wordName = "d:/office/Office.docx";
        OutputStreamWriter outputStreamWriter = null;
        try {
            XWPFDocument document = new XWPFDocument(new FileInputStream(wordName));
            XHTMLOptions options = XHTMLOptions.create();
            // 存放图片的文件夹
            options.setExtractor(new FileImageExtractor(new File("d:/office/image")));
            // html中图片的路径
            options.URIResolver(new BasicURIResolver("image"));
            outputStreamWriter = new OutputStreamWriter(new FileOutputStream("d:/office/Office.html"), "utf-8");
            XHTMLConverter xhtmlConverter = (XHTMLConverter) XHTMLConverter.getInstance();
            xhtmlConverter.convert(document, outputStreamWriter, options);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (outputStreamWriter != null) {
                try {
                    outputStreamWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("=====Word转Html成功");
    }
}
