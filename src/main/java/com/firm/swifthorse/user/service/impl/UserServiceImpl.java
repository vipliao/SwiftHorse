package com.firm.swifthorse.user.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.firm.swifthorse.assessory.service.IAssessoryService;
import com.firm.swifthorse.assessory.vo.AssessoryVO;
import com.firm.swifthorse.base.service.impl.BaseServiceImpl;
import com.firm.swifthorse.base.utils.GenerateUuidHelper;
import com.firm.swifthorse.user.entity.UserEntity;
import com.firm.swifthorse.user.service.IUserService;
import com.firm.swifthorse.user.vo.UserVO;
import com.firm.swifthorse.user.vo.WeeklyGrowthVO;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserEntity, UserVO> implements IUserService {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private IAssessoryService assessoryService;

	@Override
	@Transactional
	public UserVO login(UserVO vo) throws Exception {
		if (vo == null) {
			throw new Exception("没有登录信息");
		}
		if (vo.getPhone() == null || vo.getPhone().equals("")) {
			throw new Exception("没有登录phone信息");
		}
		if (vo.getPassword() == null || vo.getPassword().equals("")) {
			throw new Exception("没有登录password信息");
		}
		String sql = "select * from swift_user_info where 1=1 and   phone='" + vo.getPhone()+"'";
		List<UserVO> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<UserVO>(UserVO.class));
		if(users == null || users.size()<=0){
			throw new Exception("查询用户不存在！");
		}
		if(users.size() >1){
			throw new Exception("查询的用户存在多个！");
		}
		
		UserVO user = users.get(0);
		if(!vo.getPassword().equals(user.getPassword()) ){
			throw new Exception("密码错误！");
		}
		if (user != null && user.getId() != null && !user.getId().equals("")) {
			if(user.getDr()==1){
				throw new Exception("用户已冻结！");
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			java.util.Date date = new java.util.Date();
			user.setLastLoginTime(date);
			String lastLoginTime = sdf.format(date);
			String updateSql = "update swift_user_info set last_login_time='" + lastLoginTime
					+ "' where 1=1 and phone='" + user.getPhone() + "'";
			jdbcTemplate.update(updateSql);
			List<UserVO> list = new ArrayList<>();
			list.add(user);
			return queryAssessorys(list).get(0);
		} else {
			throw new Exception("输入的登录信息不正确！");
		}

	}

	@Override
	@Transactional
	public void deleteByPhone(String phone) throws Exception {
		String sql = "delete from swift_user_info where phone = '" + phone + "'";
		jdbcTemplate.update(sql);
	}

	@Override
	public UserVO queryUserByPhone(String phone) throws Exception {
		String sql = "select * from swift_user_info where 1=1 and phone='" + phone + "'";
		List<UserVO> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<UserVO>(UserVO.class));
		if(users == null || users.size()<=0){
			throw new Exception("查询用户不存在！");
		}
		if(users.size() >1){
			throw new Exception("查询的用户存在多个！");
		}
		UserVO user = users.get(0);
		if (user != null && user.getId() != null && !user.getId().equals("")) {
			List<UserVO> list = new ArrayList<>();
			list.add(user);
			return queryAssessorys(list).get(0);
		}
		return null;

	}
	
	@Override
	public UserVO findVOById(String id, Class<UserVO> cls) throws Exception {
		String sql = "select * from swift_user_info where 1=1 and id='"+id+"'";
		List<UserVO> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<UserVO>(UserVO.class));
		if(users==null || users.size()<=0){
			throw new Exception("没有查询到对应的用户");
		}
		if(users.size() >1){
			throw new Exception("对应的用户不唯一");
		}
		return queryAssessorys(users).get(0);
	}

	@Override
	@Transactional
	public UserVO saveSignAndPoints(Map<String, Object> param) throws Exception {
		if (param == null) {
			throw new Exception("传入参数为空！");
		}
		if (!param.containsKey("phone")) {
			throw new Exception("传入参数phone为空！");
		}
		if (!param.containsKey("points") && !param.containsKey("signNum")) {
			throw new Exception("至少传入一个参数points或者signNum！");
		}
		String phone = (String) param.get("phone");
		StringBuilder sql = new StringBuilder("update swift_user_info set ");
		if (param.containsKey("points")) {
			sql.append("points ='" + param.get("points") + "'");
		}
		if (param.containsKey("signNum")) {
			sql.append(" ,sign_num ='" + param.get("signNum") + "'");
		}
		sql.append(" where 1=1 and  phone='" + phone + "'");
		jdbcTemplate.update(sql.toString());
		return queryUserByPhone(phone);
	}

	@Override
	public Page<UserVO> queryUserList(Pageable pageable, Map<String, Object> map) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("Select * from  swift_user_info where 1=1");
		if(map !=null){
			if(map.containsKey("isAuth")){
				int isAuth = (int) map.get("isAuth");
				if (isAuth == 0) {
					sql.append(" and is_auth=0");
				} else if (isAuth == 1) {
					sql.append(" and is_auth=1");
				} else if (isAuth == 2) {
					sql.append(" and is_auth=2");
				}
			}
			if(map.containsKey("dr")){
				int dr = (int) map.get("dr");
				if(dr==0){
					sql.append(" and dr=0");
				}else if(dr==1){
					sql.append(" and dr=1");
				}
			}
		}
		
		int total =  getTotalCount(sql.toString());
		if(pageable != null){
			sql.append(" limit " + pageable.getPageNumber() * pageable.getPageSize() + "," + pageable.getPageSize());
		}
		List<UserVO> list = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<UserVO>(UserVO.class));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String now = sdf.format(new Date());
		int dailyActivity = dailyActivity(now);
		if (list != null && list.size() > 0) {
			list.get(0).setDailyActivity(dailyActivity);
			queryAssessorys(list);
			return new PageImpl(list, pageable, pageable != null ? total : (long) list.size());
		}
		return null;
	}
	
	
	private int getTotalCount(String sql) {
	      String totalSql = "select count(1) from (" + sql + ") t";
	      Integer total = (Integer)this.jdbcTemplate.queryForObject(totalSql, Integer.class);
	      return total.intValue();
	}

	@Override
	@Transactional
	public UserVO recomCodePoints(String recomCode, String currentUserId, int points) throws Exception {
		String sql = "select * from swift_user_info where 1=1 and recom_code ='" + recomCode + "'";
		List<UserVO> userVOs = jdbcTemplate.queryForList(sql, UserVO.class);
		if (userVOs == null || userVOs.size() <= 0) {
			throw new Exception("没有查询到此推荐码相应的用户");
		}
		String sql1 = "update swift_user_info set points=points+" + points + " where id in ('" + currentUserId + "','"
				+ userVOs.get(0).getId() + "')";
		jdbcTemplate.update(sql1);
		List<UserVO> user = jdbcTemplate.query("select * from swift_user_info where 1=1 and id='" + currentUserId + "'",
				new BeanPropertyRowMapper<>(UserVO.class));
		if (user != null && user.size() > 0) {
			return queryAssessorys(user).get(0);
		}
		return null;
	}

	@Override
	@Transactional
	public UserVO save(UserVO vo, Class<UserEntity> clazzE, Class<UserVO> clazzV) throws Exception {
		if (vo == null) {
			throw new Exception("没有数据!");
		}
		if (vo.getPhone() == null || vo.getPhone().equals("")) {
			throw new Exception("phone没有数据!");
		}
		if(vo.getId()==null || vo.getId().equals("")){//新增
			String sql = "select count(1) from swift_user_info where 1=1 and phone='" + vo.getPhone() + "'";
			Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
			if (count.intValue() > 0) {
				throw new Exception("已存在手机号为" + vo.getPhone() + "数据");
			}
			// 生成推荐码
			vo.setRecomCode(GenerateUuidHelper.generateShortUuid());
		}	
		UserVO user = super.save(vo, clazzE, clazzV);
		if (user != null && user.getId() != null && !user.getId().equals("")) {
			if (vo.getAssessorys() != null && vo.getAssessorys().size() > 0) {
				List<String> delAIds= new ArrayList<>();
				List<String> addAIds = new ArrayList<>();
				for(AssessoryVO avo:vo.getAssessorys()){
					if(avo.getDr()==1){
						delAIds.add(avo.getId());
					}else if(avo.getBusinessId() !=null && !avo.getBusinessId().equals("")){
						addAIds.add(avo.getId());
					}
				}
				if(delAIds !=null && delAIds.size()>0){
					String idList = delAIds.toString().replaceAll(" ", "").replaceAll("\\,", "\\'\\,\\'")
							.replaceAll("\\[", "\\('").replaceAll("\\]", "\\')");
					String sql ="delete from invest_assessory where id in "+idList;
					jdbcTemplate.update(sql);
				}
				if(addAIds !=null && addAIds.size()>0){
					String idList = addAIds.toString().replaceAll(" ", "").replaceAll("\\,", "\\'\\,\\'")
							.replaceAll("\\[", "\\('").replaceAll("\\]", "\\')");
					String sql ="update  invest_assessory set business_id='"+user.getId()+"' where id in "+idList;
					jdbcTemplate.update(sql);
				}
			}
			List<UserVO> list = new ArrayList<>();
			list.add(user);
			return queryAssessorys(list).get(0);
		}
		return null;
	}

	private List<UserVO> queryAssessorys(List<UserVO> users) throws Exception {
		if (users == null || users.size() <= 0) {
			return null;
		}
		List<String> userIds = new ArrayList<>();
		for (UserVO vo : users) {
			userIds.add(vo.getId());
		}
		List<AssessoryVO> assessorys = assessoryService.queryAssessorys(userIds);
		if (assessorys != null && assessorys.size() > 0) {
			for (AssessoryVO assessory : assessorys) {
				List<AssessoryVO> a = new ArrayList<>();
				for (UserVO b : users) {
					if (b.getId().equals(assessory.getBusinessId())) {
						a.add(assessory);
					}
					b.setAssessorys(a);
				}
			}
		}
		return users;
	}

	@Override
	public int dailyActivity(String day) throws Exception {
		if (day == null) {
			throw new Exception("请输入需要查询的时间");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date time = sdf.parse(day);
		String beginTime = sdf.format(time) + " 00:00:00";
		String endTime = sdf.format(time) + " 23:59:59";
		String sql = "select count(1) from swift_user_info where 1=1 and last_login_time BETWEEN '" + beginTime
				+ "' AND '" + endTime + "'";
		Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
		return count.intValue();
	}

	@Override
	public int dailyRegistCount(String day) throws Exception {
		if (day == null) {
			throw new Exception("请输入需要查询的时间");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date time = sdf.parse(day);
		String beginTime = sdf.format(time) + " 00:00:00";
		String endTime = sdf.format(time) + " 23:59:59";
		String sql = "select count(1) from swift_user_info where 1=1 and create_time BETWEEN '" + beginTime + "' AND '"
				+ endTime + "'";
		Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
		return count.intValue();
	}

	@Override
	public List<WeeklyGrowthVO> weeklyGrowth(String day) throws Exception {
		if (day == null) {
			throw new Exception("请输入需要查询的时间");
		}

		List<String> days = new ArrayList<>();
		days.add(day);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		// 向前推一天
		Date beforeOne = beforeDay(day);
		String beforeOneStr = sdf.format(beforeOne);
		days.add(beforeOneStr);
		// 向前推两天
		Date beforeTow = beforeDay(beforeOneStr);
		String beforeTowStr = sdf.format(beforeTow);
		days.add(beforeTowStr);
		// 向前推三天
		Date beforeThree = beforeDay(beforeTowStr);
		String beforeThreeStr = sdf.format(beforeThree);
		days.add(beforeThreeStr);
		// 向前推四天
		Date beforeFour = beforeDay(beforeThreeStr);
		String beforeFourStr = sdf.format(beforeFour);
		days.add(beforeFourStr);
		// 向前推五天
		Date beforeFive = beforeDay(beforeFourStr);
		String beforeFiveStr = sdf.format(beforeFive);
		days.add(beforeFiveStr);
		// 向前推六天
		Date beforeSix = beforeDay(beforeFiveStr);
		String beforeSixStr = sdf.format(beforeSix);
		days.add(beforeSixStr);

		StringBuilder sb = new StringBuilder();
		joinSql(sb, days.toArray(new String[days.size()]));
		List<WeeklyGrowthContentVO> weeklyGrowthContentVOs = jdbcTemplate.query(sb.toString(),
				new RowMapper<WeeklyGrowthContentVO>() {
					public WeeklyGrowthContentVO mapRow(ResultSet rs, int rowNum) throws SQLException {
						WeeklyGrowthContentVO vo = new WeeklyGrowthContentVO();
						vo.setNum(rs.getInt("num"));
						vo.setTime(rs.getString("time"));
						return vo;
					}

				});

		List<WeeklyGrowthVO> list = new ArrayList<>();
		if (weeklyGrowthContentVOs != null && weeklyGrowthContentVOs.size() > 0) {
			for (WeeklyGrowthContentVO vo : weeklyGrowthContentVOs) {
				WeeklyGrowthVO weeklyGrowthVO = new WeeklyGrowthVO();
				String name = vo.getTime();
				weeklyGrowthVO.setName(name);
				Object[] ob = new Object[2];
				ob[0] = name;
				ob[1] = vo.getNum();
				weeklyGrowthVO.setValue(ob);
				list.add(weeklyGrowthVO);
			}
		}
		return list;
	}

	private Date beforeDay(String day) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date time = sdf.parse(day);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		// 向前推一天
		calendar.add(Calendar.DAY_OF_WEEK, -1);
		Date beforeOne = calendar.getTime();
		return beforeOne;
	}

	private StringBuilder joinSql(StringBuilder sb, String[] arr) {

		if (arr == null || arr.length <= 0) {
			return null;
		}
		for (int i = 0; i < arr.length; i++) {
			sb.append(" select count(1) num,'" + arr[i]
					+ "' time from swift_user_info where 1=1 and date_format(create_time, '%Y-%m-%d')='" + arr[i]
					+ "'");
			if (i < arr.length - 1) {
				sb.append(" union all ");
			}
		}
		return sb;
	}

	public class WeeklyGrowthContentVO {
		private int num;
		private String time;

		public int getNum() {
			return num;
		}

		public void setNum(int num) {
			this.num = num;
		}

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

	}

	@Override
	@Transactional
	public void frozenUser(String id,int type) throws Exception {
		StringBuilder sql = new StringBuilder( );
		if(type == 0){//解冻
			sql.append("update swift_user_info set dr=0 where 1=1 and id='"+id+"'");
		}else {//冻结
			sql.append("update swift_user_info set dr=1 where 1=1 and id='"+id+"'");
		}
		
		jdbcTemplate.update(sql.toString());
		
	}

}
