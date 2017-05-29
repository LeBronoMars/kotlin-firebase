package proto.com.kotlinapp.interfaces

import proto.com.kotlinapp.models.response.SampleResponse

/**
 * Created by rsbulanon on 5/22/17.
 */
interface OnApiRequestListener {
    fun onApiRequestSuccess(result: SampleResponse)
}