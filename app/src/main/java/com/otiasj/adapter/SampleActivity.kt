package com.otiasj.adapter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.ProgressBar
import com.otiasj.adapter.core.DataChangeListener
import com.otiasj.adapter.core.Node
import com.otiasj.adapter.core.TreeSource
import com.otiasj.adapter.sample.MyData
import com.otiasj.adapter.sample.MyDataRecyclerAdapter
import com.otiasj.adapter.sample.MyDataType

class SampleActivity : AppCompatActivity() {

    private lateinit var adapter: MyDataRecyclerAdapter

    private lateinit var loadbutton: Button

    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)

        //Bind the adapter with some fake Data
        val view = this.findViewById<RecyclerView>(R.id.recyclerview)
        view.adapter = createAdapter()
        view.layoutManager = LinearLayoutManager(this)
        loadbutton = findViewById(R.id.loadButton)
        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.INVISIBLE
        loadbutton.setOnClickListener({ load() })
    }

    private var data: TreeSource? = null

    private fun load() {
        progressBar.visibility = View.VISIBLE

        Thread({
            // val root = createStaticTree(100)
            var root = createDynamicTree(100, 100)
            data = TreeSource(root)
            root.load(adapter)

            runOnUiThread({
                progressBar.visibility = View.INVISIBLE
                data?.let {
                    adapter.setSource(it)
                }
            })
        }).start()
    }

    private fun createAdapter(): RecyclerView.Adapter<*> {
        adapter = MyDataRecyclerAdapter(this)
        return adapter
    }

    //This creates a tree when you already have all the data available
    private fun createStaticTree(groupCount: Int, childCount: Int): Node {
        val root = MyData()

        //create 100 group
        createSomeGroup(root, groupCount)

        //create 100 child for each group
        root.children?.let { listOfGroup ->
            for (node in listOfGroup) {
                var group = node as? MyData
                group?.let {
                    createSomeChildren(it, childCount)
                }
            }
        }
        return root
    }

    //This creates a tree when you have to load some data asynchronously (the children are dynamic in this case)
    private fun createSemiDynamicTree(groupCount: Int, childCount: Int): Node {
        val root = MyData()

        //create 100 groups
        for (sectionIndex in 0 until groupCount) {

            //when loading a group, 100 childrens will be generated
            val section = object : MyData() {
                override fun load(listener: DataChangeListener?) {
                    progressBar.visibility = VISIBLE

                    Thread {
                        if (children == null) {
                            //create a list of children when you click on the group
                            createSomeChildren(this, childCount)
                        }

                        runOnUiThread({
                            progressBar.visibility = INVISIBLE
                            isOpen = !isOpen
                            listener?.onDataChanged(this)
                        })
                    }.start()
                }
            }
            section.index = "" + sectionIndex
            section.name = "Group $sectionIndex"
            section.type = MyDataType.HEADER
            root.addChild(section)
        }

        return root
    }


    //Fully dynamic tree
    private fun createDynamicTree(groupCount: Int, childCount: Int): Node {

        return object : MyData() {
            override fun load(listener: DataChangeListener?) {
                progressBar.visibility = VISIBLE
                Thread {
                    if (children == null) {
                        //create a list of group
                        for (childIndex in 0 until groupCount) {
                            val node = object : MyData() {
                                override fun load(listener: DataChangeListener?) {
                                    progressBar.visibility = VISIBLE
                                    Thread {
                                        if (children == null) {
                                            //create a list of children when you click on the group
                                            createSomeChildren(this, childCount)
                                        }

                                        runOnUiThread({
                                            progressBar.visibility = INVISIBLE
                                            isOpen = !isOpen
                                            listener?.onDataChanged(this)
                                        })
                                    }.start()
                                }
                            }

                            node.index = "$childIndex"
                            node.name = "Criteria $childIndex"
                            addChild(node)
                            node.type = MyDataType.ROW
                        }
                    }

                    runOnUiThread({
                        progressBar.visibility = INVISIBLE
                        isOpen = !isOpen
                        listener?.onDataChanged(this)
                    })
                }.start()
            }
        }
    }

    private fun createSomeGroup(root: MyData, groupCount: Int) {
        for (sectionIndex in 0 until groupCount) {
            val section = MyData()
            section.index = "" + sectionIndex
            section.name = "Group $sectionIndex"
            section.type = MyDataType.HEADER
            root.addChild(section)
        }
    }

    private fun createSomeChildren(root: MyData, childCount: Int) {
        for (childIndex in 0 until childCount) {
            val node = MyData()
            node.index = root.index + ".$childIndex"
            node.name = "Criteria " + root.index + "." + childIndex
            node.type = MyDataType.ROW
            root.addChild(node)
        }
    }
}
