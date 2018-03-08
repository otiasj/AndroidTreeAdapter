package com.otiasj.adapter.sample

/**
 * Created by julien on 3/7/18.
 */

enum internal class MyDataType(val type: Int) {
    HEADER(0),
    ROW(1),
    FOOTER(2);

    companion object {
        fun from(findValue: Int): MyDataType = MyDataType.values().first { it.type == findValue }
    }
}