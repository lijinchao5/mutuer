package com.xuanli.oepcms.entity;

public class UserBookEntity {
    private Long id;

    private Long userId;

    private Long bookId;

    private Integer bookUse;

    private Integer by1;

    private String by2;

    private String by3;

	private String grade;

	private Integer bookVersion;

	private String bookVolume;

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Integer getBookVersion() {
		return bookVersion;
	}

	public void setBookVersion(Integer bookVersion) {
		this.bookVersion = bookVersion;
	}

	public String getBookVolume() {
		return bookVolume;
	}

	public void setBookVolume(String bookVolume) {
		this.bookVolume = bookVolume;
	}

	// 查询用
	private Integer allSize;

	public Integer getAllSize() {
		return allSize;
	}

	public void setAllSize(Integer allSize) {
		this.allSize = allSize;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Integer getBookUse() {
        return bookUse;
    }

    public void setBookUse(Integer bookUse) {
        this.bookUse = bookUse;
    }

    public Integer getBy1() {
        return by1;
    }

    public void setBy1(Integer by1) {
        this.by1 = by1;
    }

    public String getBy2() {
        return by2;
    }

    public void setBy2(String by2) {
        this.by2 = by2;
    }

    public String getBy3() {
        return by3;
    }

    public void setBy3(String by3) {
        this.by3 = by3;
    }
}