/**
 * 数据校验方法入口
 * @param validateType
 * @param validateContent
 * @param 
 */
function validate(validateType,validateElementType,validataElement,validateRule){
	//默认校验失败状态
	var flag = true;
	//return flag;
	switch(validateType){
		case "validateNull" : 
			  flag = validateNull(validataElement);
			  break;
		case "validateIsNumber" :
			  flag = validateIsNumber(validataElement);
			  break;
		case "validateIsCharacter" : 
			  flag = validateIsCharacter(validataElement);
			  break;
		case "validateContentLength" :
			  flag = validateContentLength(validataElement);
			  break;
		default:
		      break;
	}
	return flag;
}

/**
 * 校验是否为空
 */
function validateNull(validataElement){
	//默认校验失败状态
	var flag = true;
	return flag;
}

/**
 * 校验是否为数字
 */
function validateIsNumber(validataElement){
	//默认校验失败状态
	var flag = true;
	return flag;
}

/**
 * 校验是否为字母
 */
function validateIsCharacter(validataElement){
	//默认校验失败状态
	var flag = true;
	return flag;
}

/**
 * 校验长度
 */
function validateContentLength(validataElement){
	//默认校验失败状态
	var flag = true;
	return flag;
}
/**
 * 根据规则校验
 * @param {} validataElement
 * @param {} validateRule
 * @return {}
 */
function validateByRule(validataElement,validateRule){
	//默认校验失败状态
	var flag = true;
	return flag;
}