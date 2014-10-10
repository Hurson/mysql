package com.avit.lucene;

import java.util.List;


public class PageBean {
    public static void main(String[] args) {
        PageBean pageBean = new PageBean(10, 25, 2);

        System.out.println(pageBean);
        
        pageBean.setPageNo(0);
        System.out.println(pageBean);
        pageBean.setPageNo(3);
        System.out.println(pageBean);
        pageBean.setPageNo(4);
        System.out.println(pageBean);
        
        pageBean.setRowCount(100);
        System.out.println(pageBean);
    }
    private List data;
    
    
    public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}
	private int pageSize;
    private int rowCount;

    private int pageCount;

    private int currentPage;
    private int start;

    private int end;

    /**
     * 不分页
     * 
     */
    public PageBean() {
        this(-1, 0, 0);
    }

    /**
     * 
     * pageSize 每页大小<br>
     * rowCount 总行数<br>
     * pageNo 第几页<br>
     * 
     * <pre>
     * eg:
     * &#47;&#47;每页2条记录，一共5条记录，当前页为第0页
     * PageBean pageBean = new PageBean(2, 5, 0);
     * 
     * &#47;&#47;start=0, end=1
     * pageBean.getStart();  &#47;&#47;返回开始位置0
     * pageBean.getEnd();    &#47;&#47;返回结束位置1
     * 
     * 
     * pageBean.setPageNo(2);&#47;&#47;设置获取第二页
     * &#47;&#47;start=2, end=3
     * pageBean.getStart();  &#47;&#47;返回开始位置2
     * pageBean.getEnd();    &#47;&#47;返回结束位置3
     * 
     * </pre>
     * 
     * @param pageSize
     * @param rowCount
     * @param pageNo
     */
    public PageBean(int pageSize, int rowCount, int pageNo) {
        this.pageSize = pageSize;
        this.rowCount = rowCount;
        this.currentPage = pageNo;
        
        calculate();
    }

    private void calculate() {
        if (pageSize < 1 || rowCount == 0) {
            pageSize = -1;
        } else {
            if (rowCount % pageSize == 0) {
                pageCount = rowCount / pageSize;
            } else {
                pageCount = rowCount / pageSize + 1;
            }
        }
        
        if (pageSize == -1 || rowCount == 0) {
            start = -1;
            end = -1;
            currentPage = -1;

            return;
        }

        int pageNnmber = this.currentPage;

        if (pageNnmber > pageCount) {
            pageNnmber = pageCount;
        }

        if (pageNnmber < 1) {
            pageNnmber = 1;
        }

        currentPage = pageNnmber;

        start = (pageNnmber - 1) * pageSize;
        end = pageNnmber * pageSize - 1;

        if (end > rowCount) {
            end = rowCount;
        }

        if (this.pageCount <= 0) {
            this.pageCount = 1;
        }

        if (this.currentPage <= 0) {
            this.currentPage = 1;
        }
    }


    public int getCurrentPage() {
        return currentPage;
    }

    public int getEnd() {
        return end;
    }

    public int getPageCount() {
        return pageCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getStart() {
        return start;
    }

    public boolean isUsePage() {
        return pageSize == -1 ? false : true;
    }

    public void setPageNo(int pageNo) {
        this.currentPage = pageNo;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PageBean [pageSize=").append(pageSize).append(", rowCount=").append(rowCount)
                .append(", pageCount=").append(pageCount).append(", currentPage=").append(currentPage)
                .append(", start=").append(start).append(", end=").append(end).append("]");
        return builder.toString();
    }
}
