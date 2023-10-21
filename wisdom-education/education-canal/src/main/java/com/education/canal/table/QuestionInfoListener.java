package com.education.canal.table;

import com.education.canal.CanalTableListener;
import com.education.common.enums.TableEnum;
import com.education.model.entity.QuestionInfo;
import org.springframework.stereotype.Component;

/**
 * @author zengjintao
 * @version 1.6.4
 * @create_at 2021年9月30日 0030 16:32
 */
@Component
public class QuestionInfoListener implements CanalTableListener<QuestionInfo> {


    @Override
    public void onInsert(QuestionInfo tableData) {

    }

    @Override
    public void onDelete(QuestionInfo tableData) {

    }

    @Override
    public void onUpdate(QuestionInfo newTableData, QuestionInfo oldTableData) {

    }

    @Override
    public String getTableName() {
        return TableEnum.QUESTION.getName();
    }
}
