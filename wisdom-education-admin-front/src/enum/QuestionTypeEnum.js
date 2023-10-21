/**
 * 试题类型枚举
 * @type {
 * {CALCULATION_QUESTION: {code: number, value: string},
 * SYNTHESIS_QUESTION: {code: number, value: string},
 * FILL_QUESTION: {code: number, value: string},
 * JUDGMENT_QUESTION: {code: number, value: string},
 * MULTIPLE_QUESTION: {code: number, value: string},
 * SINGLE_QUESTION: {code: number, value: string},
 * INDEFINITE_ITEM_QUESTION: {code: number, value: string}}}
 */
const QuestionType = {
  SINGLE_QUESTION: {
    'code': 1,
    'value': '单选题'
  },

  MULTIPLE_QUESTION: {
    'code': 2,
    'value': '多选题'
  },

  FILL_QUESTION: {
    'code': 3,
    'value': '填空题'
  },

  SYNTHESIS_QUESTION: {
    'code': 4,
    'value': '综合题'
  },

  INDEFINITE_ITEM_QUESTION: {
    'code': 5,
    'value': '不定项题'
  },

  JUDGMENT_QUESTION: {
    'code': 6,
    'value': '判断题'
  },

  CALCULATION_QUESTION: {
    'code': 7,
    'value': '计算题'
  }
}

/**
 * 根据code获取试题类型名称
 * @param code
 * @returns {*}
 */
export function getByCode (code) {
  let values = Object.values(QuestionType)
  for (let i = 0; i < values.length; i++) {
    if (code === values[i].code) {
      return values[i].value
    }
  }
}

export default QuestionType
