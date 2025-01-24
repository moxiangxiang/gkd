package li.songe.gkd.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import li.songe.gkd.data.RawSubscription
import li.songe.gkd.util.map
import li.songe.gkd.util.subsIdToRawFlow
import kotlin.collections.get

@Composable
fun useSubs(subsId: Long?): RawSubscription? {
    val scope = rememberCoroutineScope()
    return remember(subsId) { subsIdToRawFlow.map(scope) { it[subsId] } }.collectAsState().value
}

@Composable
fun useSubsGroup(
    subs: RawSubscription?,
    groupKey: Int?,
    appId: String?,
): RawSubscription.RawGroupProps? {
    return remember(subs, groupKey, appId) {
        if (subs != null && groupKey != null) {
            if (appId != null) {
                subs.apps.find { it.id == appId }?.groups?.find { it.key == groupKey }
            } else {
                subs.globalGroups.find { it.key == groupKey }
            }
        } else {
            null
        }
    }
}