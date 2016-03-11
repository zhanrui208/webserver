package com.web.base.util;

import java.util.Random;

public class RandomGenerator {

	private static String[] familyNames = { "爱新觉罗", "赵", "钱", "孙", "李", "周",
			"吴", "郑", "王", "冯", "陈", "楮", "卫", "蒋", "沈", "韩", "杨", "朱", "秦",
			"尤", "许", "何", "吕", "施", "张", "孔", "曹", "严", "华", "金", "魏", "陶",
			"姜", "戚", "谢", "邹", "喻", "柏", "水", "窦", "章", "云", "苏", "潘", "葛",
			"奚", "范", "彭", "郎", "鲁", "韦", "昌", "马", "苗", "凤", "花", "方", "俞",
			"任", "袁", "柳", "酆", "鲍", "史", "唐", "费", "廉", "岑", "薛", "雷", "贺",
			"倪", "汤", "滕", "殷", "罗", "毕", "郝", "邬", "安", "常", "乐", "于", "时",
			"傅", "皮", "卞", "齐", "康", "伍", "余", "元", "卜", "顾", "孟", "平", "黄",
			"和", "穆", "萧", "尹", "姚", "邵", "湛", "汪", "祁", "毛", "禹", "狄", "米",
			"贝", "明", "臧", "计", "伏", "成", "戴", "谈", "宋", "茅", "庞", "熊", "纪",
			"舒", "屈", "项", "祝", "董", "梁", "杜", "阮", "蓝", "闽", "席", "季", "麻",
			"强", "贾", "路", "娄", "危", "江", "童", "颜", "郭", "梅", "盛", "林", "刁",
			"锺", "徐", "丘", "骆", "高", "夏", "蔡", "田", "樊", "胡", "凌", "霍", "虞",
			"万", "支", "柯", "昝", "管", "卢", "莫", "经", "房", "裘", "缪", "干", "解",
			"应", "宗", "丁", "宣", "贲", "邓", "郁", "单", "杭", "洪", "包", "诸", "左",
			"石", "崔", "吉", "钮", "龚", "程", "嵇", "邢", "滑", "裴", "陆", "荣", "翁",
			"荀", "羊", "於", "惠", "甄", "麹", "家", "封", "芮", "羿", "储", "靳", "汲",
			"邴", "糜", "松", "井", "段", "富", "巫", "乌", "焦", "巴", "弓", "牧", "隗",
			"山", "谷", "车", "侯", "宓", "蓬", "全", "郗", "班", "仰", "秋", "仲", "伊",
			"宫", "宁", "仇", "栾", "暴", "甘", "斜", "厉", "戎", "祖", "武", "符", "刘",
			"景", "詹", "束", "龙", "叶", "幸", "司", "韶", "郜", "黎", "蓟", "薄", "印",
			"宿", "白", "怀", "蒲", "邰", "从", "鄂", "索", "咸", "籍", "赖", "卓", "蔺",
			"屠", "蒙", "池", "乔", "阴", "鬱", "胥", "能", "苍", "双", "闻", "莘", "党",
			"翟", "谭", "贡", "劳", "逄", "姬", "申", "扶", "堵", "冉", "宰", "郦", "雍",
			"郤", "璩", "桑", "桂", "濮", "牛", "寿", "通", "边", "扈", "燕", "冀", "郏",
			"浦", "尚", "农", "温", "别", "庄", "晏", "柴", "瞿", "阎", "充", "慕", "连",
			"茹", "习", "宦", "艾", "鱼", "容", "向", "古", "易", "慎", "戈", "廖", "庾",
			"终", "暨", "居", "衡", "步", "都", "耿", "满", "弘", "匡", "国", "文", "寇",
			"广", "禄", "阙", "东", "欧", "殳", "沃", "利", "蔚", "越", "夔", "隆", "师",
			"巩", "厍", "聂", "晁", "勾", "敖", "融", "冷", "訾", "辛", "阚", "那", "简",
			"饶", "空", "曾", "毋", "沙", "乜", "养", "鞠", "须", "丰", "巢", "关", "蒯",
			"相", "查", "后", "荆", "红", "游", "竺", "权", "逑", "盖", "益", "桓", "公",
			"万俟", "司马", "上官", "欧阳", "夏侯", "诸葛", "闻人", "东方", "赫连", "皇甫", "尉迟",
			"公羊", "澹台", "公冶", "宗政", "濮阳", "淳于", "单于", "太叔", "申屠", "公孙", "仲孙",
			"轩辕", "令狐", "锺离", "宇文", "长孙", "慕容", "鲜于", "闾丘", "司徒", "司空", "丌官",
			"司寇", "仉", "督", "子车", "颛孙", "端木", "巫马", "公西", "漆雕", "乐正", "壤驷",
			"公良", "拓拔", "夹谷", "宰父", "谷梁", "晋", "楚", "阎", "法", "汝", "鄢", "涂",
			"钦", "段干", "百里", "东郭", "南门", "呼延", "归", "海", "羊舌", "微生", "岳", "帅",
			"缑", "亢", "况", "後", "有", "琴", "梁丘", "左丘", "东门", "西门", "商", "牟",
			"佘", "佴", "伯", "赏", "南宫", "墨", "哈", "谯", "笪", "年", "爱", "阳", "佟" };
	
	private static String[] boyNames = { "绍祖", "继祖", "孝先", "敬先", "成祖", "广嗣",
			"延嗣", "裕孙", "蕃孙", "绍箕", "绍袭", "绳武", "克武", "继业", "显祖", "亮祖", "光祖",
			"延祖", "怀祖", "念祖", "怡祖", "悦祖", "建国", "振国", "兴国", "济世", "柱国", "扶中",
			"爱民", "民子", "泽民", "民生", "公朴", "景贤", "慕圣", "慕东", "希孟", "华东", "希晏",
			"希圣", "希周", "宗尼", "成才", "博学", "勇军", "前进", "连良", "学忠", "宗仁", "学智",
			"克勤", "宗谦", "德良", "尚德", "书诚", "正直", "德福", "德友", "长寿", "长龄", "长生",
			"长庚", "康生", "平安", "彭寿", "延寿", "益寿", "龟龄", "龟寿", "介官", "有官", "太山",
			"秀才", "富贵", "财旺", "有财", "进财", "盛富", "金银", "宝玉", "虎豹", "虎子", "大虎",
			"二虎", "山虎", "山豹", "长空", "天宇", "万里", "江山", "高山", "山峰", "大海", "长河",
			"刚", "正", "直", "真", "钢", "铁", "金", "铜", "银", "石", "山", "木", "虎",
			"豹", "志强", "志坚", "志刚", "铁民", "大力", "如山", "如刚", "千均", "铁柱", "刚强",
			"劲松", "劲草", "浪舟", "海鸥", "旭东", "大春", "石峰", "浩天", "浩然", "石泉", "百坚",
			"博", "木树", "能辉", "敏雄", "龙彬", "锐", "无忌" };
	
	private static String[] girlNames = { "秀秀", "娟娟", "英子", "晓慧", "巧佳", "美丽",
			"娜娜", "静静", "丽丽", "秀", "娟", "英", "华", "慧", "巧", "美", "娜", "静", "淑",
			"惠", "珠", "翠", "雅", "芝", "玉", "萍", "红", "娥", "玲", "芬", "芳", "燕",
			"彩", "春", "菊", "兰", "凤", "洁", "梅", "琳", "素", "云", "莲", "真", "环",
			"雪", "荣", "爱", "妹", "霞", "香", "月", "莺", "媛", "艳", "瑞", "凡", "佳",
			"嘉", "琼", "勤", "珍", "贞", "莉", "桂", "娣", "叶", "璧", "璐", "娅", "琦",
			"晶", "妍", "茜", "秋", "珊", "莎", "锦", "黛", "青", "倩", "婷", "姣", "婉",
			"娴", "瑾", "颖", "露", "瑶", "怡", "婵", "雁", "蓓", "纨", "仪", "荷", "丹",
			"蓉", "眉", "君", "琴", "蕊", "薇", "菁", "梦", "岚", "苑", "婕", "馨", "瑗",
			"琰", "韵", "融", "园", "艺", "咏", "卿", "聪", "澜", "纯", "毓", "悦", "昭",
			"冰", "爽", "琬", "茗", "羽", "希", "宁", "欣", "飘", "育", "滢", "馥", "筠",
			"柔", "竹", "霭", "凝", "晓", "欢", "霄", "枫", "芸", "菲", "寒", "伊", "亚",
			"宜", "可", "姬", "舒", "影", "荔", "枝", "思", "丽" };

	private static char[] charNum_  = {'a','b','c','d','e','f','g','h','i','j','k','l',
		'm','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F',
		'G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
		'0','1','2','3','4','5','6','7','8','9','_'};
	
	private static char[] chars = {'a','b','c','d','e','f','g','h','i','j','k','l','m',
		'n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G',
		'H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
	
	private static char[] nums = {'0','1','2','3','4','5','6','7','8','9'};
	
	
	private static String[] phonehead = {"130","131","132","133","134","135","136",
		"137","138","139","150","151","152","153","154","155","156","157","158","159",
		"180","181","182","183","185","186","187","188","189"};
	
	/**
	 * 生成字母数据下划线格式的字符串，字母区分大小写
	 * @param lenth
	 * @return
	 */
	public static String genRandomCharNum(int lenth) {
		
		final int maxNum = charNum_.length;
		int i; // 生成的随机数
		int count = 0; 
		StringBuffer pwd = new StringBuffer();
		Random r = new Random();
		while (count < lenth) {
			
			// 生成随机数，取绝对值，防止生成负数，
			i = Math.abs(r.nextInt(maxNum));

			if (i >= 0 && i < charNum_.length) {
				pwd.append(charNum_[i]);
				count++;
			}
		}
		return pwd.toString();
	}
	
	/**
	 * 生成指定长度的包含大小写字母的字符串
	 * @param lenth
	 * @return
	 */
	public static String genRandomChar(int lenth) {

		final int maxNum = chars.length;
		int i; // 生成的随机数
		int count = 0; 

		StringBuffer pwd = new StringBuffer();
		Random r = new Random();
		while (count < lenth) {
			
			// 生成随机数，取绝对值，防止生成负数
			i = Math.abs(r.nextInt(maxNum));

			if (i >= 0 && i < chars.length) {
				pwd.append(chars[i]);
				count++;
			}
		}
		return pwd.toString();
	}

	/**
	 * 生成指定长度的数字串
	 * @param lenth
	 * @return
	 */
	public static String genRandomNum(int lenth) {

		final int maxNum = nums.length;
		int i; // 生成的随机数
		int count = 0;
		StringBuffer pwd = new StringBuffer("");
		Random r = new Random();
		while (count < lenth) {
			// 生成随机数，取绝对值，防止生成负数，

			i = Math.abs(r.nextInt(maxNum));

			if (i >= 0 && i < nums.length) {
				pwd.append(nums[i]);
				count++;
			}
		}
		return pwd.toString();
	}

	/**
	 * 生成格式如：+86 13800138000格式的手机号码
	 * @return
	 */
	public static String genRandomPhoneNum() {

		final int max = phonehead.length;
		Random r = new Random();
		return "+86 "+phonehead[Math.abs(r.nextInt(max))] + genRandomNum(8);
	}

	/**
	 * 随机生成中文名称
	 * @return
	 */
	public static String genRandomCNName() {
		
		Random r = new Random();
		String familyName = familyNames[r.nextInt(familyNames.length)];
		String firstName = null;
		if (r.nextBoolean() == true) {
			firstName = boyNames[r.nextInt(boyNames.length)];
		} else {
			firstName = girlNames[r.nextInt(girlNames.length)];
		}

		return familyName + firstName;
	}
	
	public static void main(String[] arg) {

		System.out.println(genRandomPhoneNum());
		System.out.println(genRandomPhoneNum());
		System.out.println(genRandomPhoneNum());
		System.out.println(genRandomPhoneNum());
		System.out.println(genRandomCNName());
		System.out.println(genRandomCNName());
		System.out.println(genRandomCNName());
		System.out.println(genRandomCNName());
	}
}