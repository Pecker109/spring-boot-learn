package com.mybatis.plus.example.helper;


import cn.hutool.core.collection.CollectionUtil;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.*;
import org.apache.ibatis.mapping.BoundSql;

import java.util.List;

public class DataPermissionSqlHandle {

    /**
     * 通用组装(也可根据不同的过滤数据类型实现特殊的组装)
     * @param boundSql
     * @return
     */
    public static String getPermissionSql(BoundSql boundSql) {
        String sql = expressionItemSql(boundSql);
        DataPermission dataPermission = DataPermissionHelper.getDataPermission();
        List<String> userIds = dataPermission.getUserIds();
        //pageHelper生成count语句时如果查询字段中不带动态参数、函数时会将所有的查询字段替换成count(0)
        //所以此处为了避免最外层包的sql被直接替换加了一个now()函数
        StringBuilder sqlBuilder = new StringBuilder("select dp.*,now() from (");
        sqlBuilder.append(sql).append(") dp where dp.create_by in (");
        for (int i = 0; i < userIds.size(); i++) {
            sqlBuilder.append("'").append(userIds.get(i)).append("'");
            if (i != userIds.size() - 1) {
                sqlBuilder.append(",");
            }
        }
        sqlBuilder.append(")");
        return sqlBuilder.toString();
    }

    /**
     * 拼装动态sql-需要进行权限控制的字段
     * @param boundSql
     * @return
     */
    private static String expressionItemSql(BoundSql boundSql) {
        String sql = boundSql.getSql();
        Statement sm;
        DataPermission dataPermission = DataPermissionHelper.getDataPermission();
        //处理动态字段
        List<String> expressionItems = dataPermission.getExpressionItem();
        if (CollectionUtil.isNotEmpty(expressionItems)) {
            try {
                sm = CCJSqlParserUtil.parse(sql);
                Select select = (Select) sm;
                assert select != null;
                SelectBody selectBody = select.getSelectBody();
                PlainSelect plainSelect = (PlainSelect) selectBody;
                Alias alias = plainSelect.getFromItem().getAlias();
                List<SelectItem> items = plainSelect.getSelectItems();
                if (CollectionUtil.isNotEmpty(expressionItems)) {
                    for (String item : expressionItems) {
                        SelectExpressionItem expressionItem = new SelectExpressionItem(CCJSqlParserUtil.parseExpression(alias.getName() + "." + item));
                        items.add(expressionItem);
                    }
                }
                plainSelect.setSelectItems(items);
                select.setSelectBody(plainSelect);
                sql = select.getSelectBody().toString();
            } catch (JSQLParserException e) {
                e.printStackTrace();
            }
        }
        return sql;
    }
}
