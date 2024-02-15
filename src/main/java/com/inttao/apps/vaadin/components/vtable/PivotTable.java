package com.inttao.apps.vaadin.components.vtable;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;

import java.util.UUID;
//@JsModule("./vtable.min.js")
//@JavaScript("./vtable.min.js")
public class PivotTable extends Div {
    private String elementId;
    private String data = "[]";
    public PivotTable() {
        UUID uuid = UUID.randomUUID();
        this.elementId = uuid.toString();
        setId(elementId);
        UI.getCurrent().getPage().addJavaScript("./frontend/vtable.min.js");
        setMinHeight("400px");
        setMinWidth("600px");
    }
    public void setupTable() {

        getElement().executeJs("const data="+ data + ";\n" +
                "\n" +
                "const option = {\n" +
                "  records:data,\n" +
                "  rows: [\"country\",\"customer\"],\n" +
                "  columns: [ \"product\", \"sub product\"],\n" +
                "  indicators: [\n" +
                "                {\n" +
                "                    \"indicatorKey\": \"amount\",\n" +
                "                    \"title\": \"Amount\",\n" +
                "                    \"width\": \"auto\",\n" +
                "                    \"showSort\": false,\n" +
                "                    \"headerStyle\":{\n" +
                "                      fontWeight: \"normal\",\n" +
                "                    },\n" +
                "                    \"format\":(value)=>{\n" +
                "                      if(value)\n" +
                "                        return '$'+Number(value).toFixed(2);\n" +
                "                      return '';\n" +
                "                    },\n" +
                "                    style:{\n" +
                "                      padding:[16,28,16,28],\n" +
                "                      color(args){\n" +
                "                        if(args.dataValue>=0)\n" +
                "                        return 'black';\n" +
                "                        return 'red'\n" +
                "                      }\n" +
                "                     }\n" +
                "                }\n" +
                "  ],\n" +
                "  \"corner\": {\n" +
                "        \"titleOnDimension\": \"row\",\n" +
                "        \"headerStyle\": {\n" +
                "            \"textStick\": true\n" +
                "    }\n" +
                "  },\n" +
                "  widthMode:'standard',\n" +
                "  enableDataAnalysis: true,\n" +
                "  defaultHeaderColWidth:150,\n" +
                "  rowHierarchyIndent: 20\n" +
                "};\n" +
                "const tableInstance = new VTable.PivotTable(document.getElementById(\""+ this.elementId + "\"), option);\n" +
                "\n" +
                "\n");
    }
    public void setData(String data) {
        this.data = data;
    }

}
