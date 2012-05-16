// package uk.ac.cranfield.thesis.client;
//
// import com.gargoylesoftware.htmlunit.javascript.host.Window;
// import com.google.gwt.user.client.ui.CaptionPanel;
// import com.google.gwt.visualization.client.AbstractDataTable;
// import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
// import com.google.gwt.visualization.client.DataTable;
// import com.google.gwt.visualization.client.visualizations.corechart.LineChart;
// import com.google.gwt.visualization.client.visualizations.corechart.Options;
//
//
// public class GraphPanel extends CaptionPanel implements Runnable
// {
//
// private LineChart chart;
//
//
// public GraphPanel()
// {
// setCaptionText("Solution");
// setStyleName("bigFontRoundedBorder");
// }
//
// @Override
// public void run()
// {
// chart = new LineChart(createTable(), createOptions());
// add(chart);
//
// }
//
// private Options createOptions()
// {
// Options options = Options.create();
// options.setWidth((Window.WINDOW_WIDTH));
// options.setHeight((Window.WINDOW_HEIGHT));
// // options.setTitle("Solution");
// return options;
// }
//
// private AbstractDataTable createTable()
// {
// DataTable data = DataTable.create();
//
// data.addColumn(ColumnType.NUMBER, "x");
// data.addColumn(ColumnType.NUMBER, "f(x)");
// data.addRows(100);
//
// for (int i = 0; i < 100; i++)
// {
// // x
// data.setValue(i, 0, i);
// // y
// data.setValue(i, 1, i * i);
// }
//
//
// return data;
// }
// }
