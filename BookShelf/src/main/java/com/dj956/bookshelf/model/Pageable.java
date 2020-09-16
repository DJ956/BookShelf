package com.dj956.bookshelf.model;

public class Pageable {

	private int pageSize = 10;
	private int maxSize;
	private int currentPage;

	public Pageable(int count, int currentPage) {
		maxSize = count / pageSize;

		if(count % pageSize > 0) {
			maxSize++;
		}

		setCurrentPage(currentPage);
	}

	public int getMaxSize() {return maxSize;}
	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	public int getCurrentPage() {return currentPage;}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {return pageSize;}

	public int getOffset() {
		return pageSize * currentPage;
	}

	public boolean isFirst() {
		return getCurrentPage() == 0;
	}

	public boolean isLast() {
		return getCurrentPage() + 1 == maxSize;
	}

	public int getNext() {return currentPage + 1;}

	public int getPrev() {return currentPage - 1;}

	@Override
	public String toString() {
		return "Pageable [pageSize=" + pageSize + ", maxSize=" + maxSize + ", currentPage=" + currentPage
				+ ", getOffset()=" + getOffset() + "]";
	}
}
