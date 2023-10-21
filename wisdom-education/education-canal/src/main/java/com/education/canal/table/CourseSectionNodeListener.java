package com.education.canal.table;

import com.education.canal.CanalTableListener;
import com.education.common.enums.TableEnum;
import com.education.model.entity.CourseSectionNode;
import org.springframework.stereotype.Component;

/**
 * @author zengjintao
 * @create_at 2021/10/6 20:31
 * @since version 1.0.3
 */
@Component
public class CourseSectionNodeListener implements CanalTableListener<CourseSectionNode> {

    @Override
    public void onInsert(CourseSectionNode tableData) {

    }

    @Override
    public void onDelete(CourseSectionNode tableData) {

    }

    @Override
    public void onUpdate(CourseSectionNode newTableData, CourseSectionNode oldTableData) {

    }

    @Override
    public String getTableName() {
        return TableEnum.COURSE_SECTION_NODE.getName();
    }
}
