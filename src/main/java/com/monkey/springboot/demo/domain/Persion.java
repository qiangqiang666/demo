/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Persion
 * Author:   44637
 * Date:     2018/10/24 19:40
 * Description: 测试类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.monkey.springboot.demo.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.omg.CORBA.PRIVATE_MEMBER;

/**
 * 〈一句话功能简述〉<br> 
 * 〈测试类〉
 *
 * @author 44637
 * @create 2018/10/24
 * @since 1.0.0
 */
@Getter
@Setter
public class Persion {
    private String name;
    private String password;
    private String code;
}