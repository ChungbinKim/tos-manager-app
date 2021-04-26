package com.example.tosmanager.model;

public class TermsSummary {
    // 서비스 이름
    private String serviceName;
    // 약관 요약 내용
    private ListContents listContents = new ListContents();
    private TableContent tableContent;
    // TODO 달력 event 목록

    public TermsSummary(String serviceName, CharSequence tableName) {
        this.serviceName = serviceName;
        tableContent = new TableContent(tableName);
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public ListContents getListContents() {
        return listContents;
    }

    public TableContent getTableContent() {
        return tableContent;
    }
}
