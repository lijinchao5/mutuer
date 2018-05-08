/**
 * @fileName:  BookController.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月18日 上午9:29:20
 */
package com.xuanli.oepcms.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.entity.BookEntity;
import com.xuanli.oepcms.entity.SectionDetail;
import com.xuanli.oepcms.entity.SectionEntity;
import com.xuanli.oepcms.entity.UnitEntity;
import com.xuanli.oepcms.service.BookSectionDetailService;
import com.xuanli.oepcms.service.BookSectionService;
import com.xuanli.oepcms.service.BookService;
import com.xuanli.oepcms.service.BookUnitService;
import com.xuanli.oepcms.util.StringUtil;
import com.xuanli.oepcms.vo.RestResult;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author QiaoYu
 */
@RestController()
// @RequestMapping(value = "/book/", method = RequestMethod.POST)
@RequestMapping(value = "/book/")
public class BookController extends BaseController {
	@Autowired
	BookService bookService;
	@Autowired
	BookUnitService bookUnitService;
	@Autowired
	BookSectionService bookSectionService;
	@Autowired
	BookSectionDetailService bookSectionDetailService;

	/**
	 * @Description: TODO 查询课本信息
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月18日 上午9:50:50
	 */

	@ApiOperation(value = "更换教材", notes = "查询教材信息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "grade", value = "年级", required = true, dataType = "String"),
			@ApiImplicitParam(name = "bookVolume", value = "教材册别", required = true, dataType = "String"), })
	@RequestMapping(value = "getBooks.do", method = RequestMethod.GET)
	public RestResult<List<BookEntity>> getBooks(String grade, String bookVolume) {
		try {
			BookEntity bookEntity = new BookEntity();
			bookEntity.setGrade(grade);
			bookEntity.setBookVolume(bookVolume);
			List<BookEntity> bookEntities = bookService.getBookEntity(bookEntity);
			return ok(bookEntities);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询书籍信息出现异常.", e);
			return failed(ExceptionCode.UNKNOW_CODE, e.getMessage());
		}
	}

	/**
	 * @Description: TODO 查询单元信息
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月18日 上午9:50:37
	 */
	@ApiOperation(value = "查询单元", notes = "查询教材的单元信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "bookId", value = "教材Id", required = true, dataType = "String"),
		@ApiImplicitParam(name = "bgrade", value = "教材册别", required = true, dataType = "Integer"),
		@ApiImplicitParam(name = "bversion", value = "教材册别", required = true, dataType = "Integer"),
		@ApiImplicitParam(name = "bvolume", value = "教材册别", required = true, dataType = "Integer"),
	})
	@RequestMapping(value = "getUnits.do", method = RequestMethod.GET)
	public RestResult<List<UnitEntity>> getUnits(String bookId,Integer bgrade,Integer bversion,Integer bvolume) {
		if (StringUtil.isEmpty(bookId) && (bgrade == null || bversion == null || bvolume == null)) {
			return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "请先选择教材");
		}
		UnitEntity unitEntity = new UnitEntity();
		unitEntity.setBookId(bookId);
		unitEntity.setBgrade(bgrade);
		unitEntity.setBversion(bversion);
		unitEntity.setBvolume(bvolume);
		try {
			List<UnitEntity> unitEntities = bookUnitService.getUnitEntity(unitEntity);
			return ok(unitEntities);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询单元信息出现异常.", e);
			return failed(ExceptionCode.UNKNOW_CODE, e.getMessage());
		}
	}

	/**
	 * @Description: TODO 查询单元详细信息
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月18日 上午9:50:37
	 */
	@ApiOperation(value = "查询单元详情", notes = "查询教材的单元信息的详情")
	@ApiImplicitParams({ @ApiImplicitParam(name = "unitId", value = "教材单元Id", required = true, dataType = "String"), })
	@RequestMapping(value = "getSections.do", method = RequestMethod.GET)
	public RestResult<List<SectionEntity>> getSections(String unitId) {
		if (StringUtil.isEmpty(unitId)) {
			return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "请先选择教材单元");
		}
		try {
			SectionEntity sectionEntity = new SectionEntity();
			sectionEntity.setUnitId(unitId);
			List<SectionEntity> sectionEntities = bookSectionService.getSectionEntity(sectionEntity);
			return ok(sectionEntities);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询单元详细信息出现异常.", e);
			return failed(ExceptionCode.UNKNOW_CODE, e.getMessage());
		}
	}

	/**
	 * @Description: TODO 获取题目信息
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月18日 上午10:16:41
	 */
	@ApiOperation(value = "查询单元章节内容", notes = "查询单元章节内容")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "sectionId", value = "教材单元章节Id", required = true, dataType = "String"), })
	@RequestMapping(value = "getSectionDetail.do", method = RequestMethod.GET)
	public RestResult<List<SectionDetail>> getSectionDetail(String sectionId) {
		if (StringUtil.isEmpty(sectionId)) {
			return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "请先选择教材章节");
		}
		try {
			SectionDetail sectionDetail = new SectionDetail();
			sectionDetail.setSectionId(sectionId);
			List<SectionDetail> sectionDetails = bookSectionDetailService.getSectionDetail(sectionDetail);
			return ok(sectionDetails);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询题目详细信息出现异常.", e);
			return failed(ExceptionCode.UNKNOW_CODE, e.getMessage());
		}
	}

	@ApiOperation(value = "根据id查询教材信息", notes = "根据id查询教材信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "bookId", value = "教材id", required = true, dataType = "Long"),
		@ApiImplicitParam(name = "bgrade", value = "教材册别", required = true, dataType = "Integer"),
		@ApiImplicitParam(name = "bversion", value = "教材册别", required = true, dataType = "Integer"),
		@ApiImplicitParam(name = "bvolume", value = "教材册别", required = true, dataType = "Integer"),
	})
	@RequestMapping(value = "getBookById.do", method = RequestMethod.GET)
	public RestResult<BookEntity> getBookById(Long bookId,Integer bgrade,Integer bversion,Integer bvolume) {
		BookEntity bookEntity = new BookEntity();
		bookEntity.setId(bookId);
		bookEntity.setGrade(bgrade + "");
		bookEntity.setBookVersion(bversion);
		bookEntity.setBookVolume(bvolume + "");
		// bookEntity.setBgrade(bgrade);
		// bookEntity.setBversion(bversion);
		// bookEntity.setBvolume(bvolume);
		List<BookEntity> bookEntities = bookService.getBookEntity(bookEntity);
		if (null != bookEntities && bookEntities.size()>0) {
			return ok(bookEntities.get(0));
		}else {
			return ok(null);
		}
	}

	@ApiOperation(value = "根据年级获取教材版本信息", notes = "根据年级获取教材版本信息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "grade", value = "年级id", required = true, dataType = "Integer") })
	@RequestMapping(value = "getBookVersion.do", method = RequestMethod.GET)
	public RestResult<List<Map<String, Object>>> getBookVersion(Integer grade) {
		return ok(bookService.getBookVersion(grade));
	}

	@ApiOperation(value = "根据年级与教材版本获取册别", notes = "根据年级与教材版本获取册别")
	@ApiImplicitParams({ @ApiImplicitParam(name = "grade", value = "年级", required = true, dataType = "String"),
			@ApiImplicitParam(name = "bookVersion", value = "教材版本", required = true, dataType = "Integer") })
	@RequestMapping(value = "getBookVolume.do", method = RequestMethod.GET)
	public RestResult<List<Map<String, Object>>> getBookVolume(String grade, Integer bookVersion) {
		return ok(bookService.getBookVolume(grade, bookVersion));
	}
}
