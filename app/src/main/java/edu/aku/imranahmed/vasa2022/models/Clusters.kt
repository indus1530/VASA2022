package edu.aku.imranahmed.vasa2022.models

import android.database.Cursor
import edu.aku.imranahmed.vasa2022.contracts.TableContracts.ClusterTable
import edu.aku.imranahmed.vasa2022.core.MainApp._EMPTY_
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by hassan.naqvi on 11/30/2016.
 */
class Clusters {
    var ID: Long = 0
    var clusterCode: String = _EMPTY_
    var geoarea: String = _EMPTY_
    var distId: String = _EMPTY_


    constructor() {
        // Default Constructor
    }

    @Throws(JSONException::class)
    fun sync(jsonObject: JSONObject): Clusters {
        clusterCode = jsonObject.getString(ClusterTable.COLUMN_CLUSTER_CODE)
        geoarea = jsonObject.getString(ClusterTable.COLUMN_GEOAREA)
        distId = jsonObject.getString(ClusterTable.COLUMN_DIST_ID)


        return this
    }

    fun hydrate(cursor: Cursor): Clusters {
        ID = cursor.getLong(cursor.getColumnIndexOrThrow(ClusterTable.COLUMN_ID))
        clusterCode =
            cursor.getString(cursor.getColumnIndexOrThrow(ClusterTable.COLUMN_CLUSTER_CODE))
        geoarea = cursor.getString(cursor.getColumnIndexOrThrow(ClusterTable.COLUMN_GEOAREA))
        distId = cursor.getString(cursor.getColumnIndexOrThrow(ClusterTable.COLUMN_DIST_ID))

        return this
    }


}