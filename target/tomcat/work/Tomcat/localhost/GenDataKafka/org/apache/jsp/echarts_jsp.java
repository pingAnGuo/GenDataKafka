/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2020-04-10 08:34:25 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class echarts_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("<head>\r\n");
      out.write("    <meta charset=\"UTF-8\">\r\n");
      out.write("    <title>ECharts折线图的绘制</title>\r\n");
      out.write("    <!-- 引入 ECharts 文件 -->\r\n");
      out.write("    <script src=\"WEB-INF/js/echarts.js\"></script>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("    <div >\r\n");
      out.write("        <input type=\"submit\" value=\"&nbsp;&nbsp;刷新&nbsp;&nbsp;\" οnclick=\"getData()\">\r\n");
      out.write("    </div>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->\r\n");
      out.write("    <div id=\"main\" style=\"width: 600px;height:400px;\"></div>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("    var now ;\r\n");
      out.write("    var data = [];\r\n");
      out.write("    var currenttime;\r\n");
      out.write("\r\n");
      out.write("    // 基于准备好的dom，初始化echarts实例\r\n");
      out.write("    var myChart = echarts.init(document.getElementById('main'));\r\n");
      out.write("    var index = 0;\r\n");
      out.write("    <c:forEach items=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${list}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("\" var=\"goods\">\r\n");
      out.write("    data[index++] = ");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${goods.y_count}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write(";\r\n");
      out.write("    </c:forEach>\r\n");
      out.write("\r\n");
      out.write("    // 指定图表的配置项和数据\r\n");
      out.write("    var option = {\r\n");
      out.write("        tooltip: {\r\n");
      out.write("            trigger: 'axis',\r\n");
      out.write("            position: function (pt) {\r\n");
      out.write("                return [pt[0], '10%'];\r\n");
      out.write("            }\r\n");
      out.write("        },\r\n");
      out.write("        title: {\r\n");
      out.write("            left: 'center',\r\n");
      out.write("            text: '报警折线图',\r\n");
      out.write("        },\r\n");
      out.write("        legend: {//图例，每条折线或者项对应的示例\r\n");
      out.write("            left: 'right',\r\n");
      out.write("            data:['报警数量']\r\n");
      out.write("        },\r\n");
      out.write("        xAxis : [ {\r\n");
      out.write("            type : 'category',//从0刻度开始\r\n");
      out.write("            boundaryGap : false,\r\n");
      out.write("            data : [\r\n");
      out.write("                <c:forEach items=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${list}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("\" var=\"g\">\r\n");
      out.write("                [\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${g.x_time}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("\"],\r\n");
      out.write("                </c:forEach>\r\n");
      out.write("            ]\r\n");
      out.write("        } ],\r\n");
      out.write("        yAxis : [ {\r\n");
      out.write("            name : '数量                       ', //Y轴提示\r\n");
      out.write("            type : 'value',\r\n");
      out.write("        } ],\r\n");
      out.write("        series : [ {\r\n");
      out.write("            \"name\" : \"报警数量\",\r\n");
      out.write("            \"type\" : \"line\",\r\n");
      out.write("            \"data\" : data,\r\n");
      out.write("            \"smooth\" : true, //主题--线条平滑\r\n");
      out.write("            \"symbol\" : 'emptycircle', //设置折线图中表示每个坐标点的符号；emptycircle：空心圆；emptyrect：空心矩形；circle：实心圆；emptydiamond：菱形\r\n");
      out.write("            itemStyle: {\r\n");
      out.write("                normal: {\r\n");
      out.write("                    color: 'rgb(255, 70, 131)'\r\n");
      out.write("                }\r\n");
      out.write("            },\r\n");
      out.write("            areaStyle: {\r\n");
      out.write("                normal: {\r\n");
      out.write("                    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{\r\n");
      out.write("                        offset: 0,\r\n");
      out.write("                        color: 'rgb(255, 158, 68)'\r\n");
      out.write("                    }, {\r\n");
      out.write("                        offset: 1,\r\n");
      out.write("                        color: 'rgb(255, 70, 131)'\r\n");
      out.write("                    }])\r\n");
      out.write("                }\r\n");
      out.write("            }\r\n");
      out.write("        }\r\n");
      out.write("        ]\r\n");
      out.write("    };\r\n");
      out.write("    // 使用刚指定的配置项和数据显示图表。\r\n");
      out.write("    myChart.setOption(option);\r\n");
      out.write("\r\n");
      out.write("    // function getData() {\r\n");
      out.write("    //     now = new Date();\r\n");
      out.write("    //     currentTime = [now.getHours(), now.getMinutes(), now.getSeconds()].join(': ');\r\n");
      out.write("    //\r\n");
      out.write("    //     $.ajax({\r\n");
      out.write("    //         type : \"post\",\r\n");
      out.write("    //         async : false, //同步执行\r\n");
      out.write("    //         url : \"/Echarts\",\r\n");
      out.write("    //         dataType : \"json\", //返回数据形式为json\r\n");
      out.write("    //         data :\r\n");
      out.write("    //             {js_time : currentTime},\r\n");
      out.write("    //         success : function(result) {\r\n");
      out.write("    //             $.each(result,function(index,item){\r\n");
      out.write("    //                 xtime.push(item.x_time);\r\n");
      out.write("    //                 data.push(item.y_count)\r\n");
      out.write("    //             });\r\n");
      out.write("    //             var option = myChart.getOption();\r\n");
      out.write("    //             option.xAxis[0].data = xtime;\r\n");
      out.write("    //             option.series[0].data = data;\r\n");
      out.write("    //             myChart.hideLoading();\r\n");
      out.write("    //             myChart.setOption(option);\r\n");
      out.write("    //             myChart.hideLoading();\r\n");
      out.write("    //\r\n");
      out.write("    //         },\r\n");
      out.write("    //         error : function() {\r\n");
      out.write("    //             alert(\"图表请求数据失败!\");\r\n");
      out.write("    //         }\r\n");
      out.write("    //     })\r\n");
      out.write("\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
