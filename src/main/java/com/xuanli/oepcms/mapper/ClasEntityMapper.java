package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xuanli.oepcms.entity.ClasEntity;
import com.xuanli.oepcms.entity.UserClasEntity;
@Mapper
public interface ClasEntityMapper {
    int deleteById(Long id);

    int insertClasEntity(ClasEntity record);

    ClasEntity selectById(Long id);

    int updateById(ClasEntity record);

	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月16日 上午9:37:23
	 */
	List<ClasEntity> selectClasEntity(ClasEntity clasEntity);
    
	
	/**删除班级,只取消老师与班级绑定关系*/
	int updateClas(Long userId);

	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月30日 上午9:35:24
	 */
	int findClasByPageTotal(ClasEntity clasEntity);

	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月30日 上午9:35:29
	 */
	List<ClasEntity> findClasByPage(ClasEntity clasEntity);

	/**@Description:  TODO
	 * @CreateName:  codelion[QiaoYu]
	 * @CreateDate:  2018年3月4日 下午1:39:10
	 */
	ClasEntity getClasInfoById(Long clasId);

	/**Title: updateUserClass 
	 * Description:  更换班级
	 * @date 2018年3月7日 下午3:20:52
	 * @param classId  
	 */
	int updateUserClass(@Param("clasId")Long clasId, @Param("userId")Long userId);

	/**Title: selectByClassId 
	 * Description:  
	 * @date 2018年3月7日 下午4:02:51
	 * @param classId
	 * @return  
	 */
	ClasEntity selectByClassId(String clasId);

	/**Title: selectStudentClassList 
	 * Description:  
	 * @date 2018年4月25日 上午9:56:30
	 * @return  
	 */
	List<ClasEntity> selectStudentClassList(Long studentId);

	/**Title: deleteStudentClass 
	 * Description:  
	 * @date 2018年4月25日 上午10:29:16
	 * @param userClasEntity
	 * @return  
	 */
	int deleteStudentClass(UserClasEntity userClasEntity);
	
}