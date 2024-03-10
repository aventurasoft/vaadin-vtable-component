package com.inttao.apps.vaadin.components.vtable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;

import java.util.List;
import java.util.UUID;

/**
 * Wrapper Pivot Table del control VTable
 * <a href="https://visactor.io/vtable/guide/table_type/Pivot_table/pivot_table_useage">...</a>
 *
 */
public class PivotTable extends Div {
    private String elementId;
    private String records = "[]";

    /***
     * The specific configuration of each indicator in the pivot table.
     */
    private List<String> indicators = List.of();
    /**
     *  The column header corresponds to the style and format configuration of various levels of dimensions.
     */
    private List<String> columns = List.of();
    /**
     *  The row header corresponds to the style and format configuration of dimensions at all levels.
     */
    private List<String> rows = List.of();

    /**
     * Enable VTable's ability to analyze perspective structures, the default is false. If you pass in custom
     * columnTree and rowTree, please turn it off. If you do not pass in columnTree and rowTree, please turn it on.
     */
    private Boolean enableDataAnalysis = true;
    /**
     * Customize the list header dimension tree (custom capability). Generally, this tree structure only needs
     * to be configured when there are special sorting requirements for dimension value
     */
    private List<String> columnTree;
    /**
     * Customize the row header dimension tree (customization capability). Generally, this tree structure needs
     * to be configured when there are special sorting requirements for dimension values.
     */
    private List<String> rowTree;
    /**
     * tree hierarchical display or tile display.
     */
    private String rowHierarchyType;
    /**
     * Whether it is necessary to hide the indicator name on the header. Default is false.
     */
    private Boolean hideIndicatorName = false;
    /**
     * Whether to display the row header, the default is true.
     */
    private Boolean showRowHeader = true;
    /**
     * Whether to display the column header, the default is true.
     */
    private Boolean showColumnHeader = true;
    /**
     * Various configurations and style customization of the corner table header.
     */
    private String corner;
    private String withMode = "standard";

    private final ObjectMapper objectMapper = new ObjectMapper();


    public PivotTable() {
        UUID uuid = UUID.randomUUID();
        this.elementId = uuid.toString();
        setId(elementId);
        UI.getCurrent().getPage().addJavaScript("./frontend/vtable.min.js");
        setMinHeight("400px");
        setMinWidth("600px");
        setCorner(getDefaultCorner());
    }

    private String getDefaultCorner() {
        return   "{ " +
                    "  \"titleOnDimension\": \"row\",\n" +
                    "   \"headerStyle\": {\n" +
                    "       \"textStick\": true\n" +
                    "    }\n" +
                    "}";
}

    public String generateOptions() {
        try {


            String columnsStr = objectMapper.writeValueAsString(columns);
            String rowsStr = objectMapper.writeValueAsString(rows);

            String options = "{" +
                    "  records:" + getRecords() + ", " +
                    //                "  rows: [\"country\",\"customer\"],\n" +
                    //                "  columns: [ \"product\", \"sub product\"],\n" +
                    " rows: " + rowsStr + ", " +
                    " columns: " + columnsStr + ", " +
                    "  indicators: " + getIndicators();
        //                    "[\n" +
        //                    "                {\n" +
        //                    "                    \"indicatorKey\": \"amount\",\n" +
        //                    "                    \"title\": \"Amount\",\n" +
        //                    "                    \"width\": \"auto\",\n" +
        //                    "                    \"showSort\": false,\n" +
        //                    "                    \"headerStyle\":{\n" +
        //                    "                      fontWeight: \"normal\",\n" +
        //                    "                    },\n" +
        //                    "                    \"format\":(value)=>{\n" +
        //                    "                      if(value)\n" +
        //                    "                        return '$'+Number(value).toFixed(2);\n" +
        //                    "                      return '';\n" +
        //                    "                    },\n" +
        //                    "                    style:{\n" +
        //                    "                      padding:[16,28,16,28],\n" +
        //                    "                      color(args){\n" +
        //                    "                        if(args.dataValue>=0)\n" +
        //                    "                        return 'black';\n" +
        //                    "                        return 'red'\n" +
        //                    "                      }\n" +
        //                    "                     }\n" +
        //                    "                }\n" +
        //                    "  ],\n" +

            if (getCorner() != null) {
                options += ",  \"corner\": " + getCorner();
            }
        //                    "{\n" +
        //                    "        \"titleOnDimension\": \"row\",\n" +
        //                    "        \"headerStyle\": {\n" +
        //                    "            \"textStick\": true\n" +
        //                    "    }\n" +
        //                    "  },\n" +
            if (getWithMode() != null) {
                options += ", \"widthMode\":'" + getWithMode() + "' ";
            }

            options += ", enableDataAnalysis:  " + getEnableDataAnalysis().toString();
            options += ", defaultHeaderColWidth:150 ";
            options += ", rowHierarchyIndent: 20 ";
            options += "}; ";
            return options;
        } catch (JsonProcessingException ex) {
            throw  new RuntimeException(ex);
        }
    }
    public void setupTable() {

            String options = "const option = " + generateOptions() + ";";

            getElement().executeJs( options +
                    "const tableInstance = new VTable.PivotTable(document.getElementById(\""+ this.elementId + "\"), option);\n");

    }
    public void setRecords(String records) {
        this.records = records;
    }
    public void setRows(List<String> rows) {
        this.rows = rows;
    }
    public void setColumns(List<String> columns) {
        this.columns = columns;
    }
    public List<String> getRows() {
        return rows;
    }

    public List<String> getColumns() {
        return columns;
    }

    public String getRecords() {
        return records;
    }

    public List<String> getIndicators() {
        return indicators;
    }

    public void setIndicators(List<String> indicators) {
        this.indicators = indicators;
    }

    private void setIndicator(Indicator indicator) {

    }
    public Boolean getEnableDataAnalysis() {
        return enableDataAnalysis;
    }

    public void setEnableDataAnalysis(Boolean enableDataAnalysis) {
        this.enableDataAnalysis = enableDataAnalysis;
    }

    public List<String> getColumnTree() {
        return columnTree;
    }

    public void setColumnTree(List<String> columnTree) {
        this.columnTree = columnTree;
    }

    public List<String> getRowTree() {
        return rowTree;
    }

    public void setRowTree(List<String> rowTree) {
        this.rowTree = rowTree;
    }

    public String getRowHierarchyType() {
        return rowHierarchyType;
    }

    public void setRowHierarchyType(String rowHierarchyType) {
        this.rowHierarchyType = rowHierarchyType;
    }

    public Boolean getHideIndicatorName() {
        return hideIndicatorName;
    }

    public void setHideIndicatorName(Boolean hideIndicatorName) {
        this.hideIndicatorName = hideIndicatorName;
    }

    public Boolean getShowRowHeader() {
        return showRowHeader;
    }

    public void setShowRowHeader(Boolean showRowHeader) {
        this.showRowHeader = showRowHeader;
    }

    public Boolean getShowColumnHeader() {
        return showColumnHeader;
    }

    public void setShowColumnHeader(Boolean showColumnHeader) {
        this.showColumnHeader = showColumnHeader;
    }

    public String getCorner() {
        return corner;
    }

    public void setCorner(String corner) {
        this.corner = corner;
    }

    public String getWithMode() {
        return withMode;
    }

    public void setWithMode(String withMode) {
        this.withMode = withMode;
    }


    public record Indicator(String indicatorKey, String title, String width, Boolean showSort
            , String headerStyle, String format, String style){};
}
