package app.luichigo15.pairly.ui.role

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import app.luichigo15.pairly.R
import app.luichigo15.pairly.ui.role.widget.PLRoleCard
import app.luichigo15.pairly.ui.theme.PLTheme

@Composable
fun PLRoleScreen(modifier: Modifier = Modifier) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeContentPadding()
            .padding(15.dp)
    ) {
        val (rolesRef, questionRef) = createRefs()
        Text(stringResource(R.string.pl_role_title), modifier = Modifier.constrainAs(questionRef){
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(rolesRef.top)
        }, color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineLarge)
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(rolesRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }, maxItemsInEachRow = 2, maxLines = 1
        ) {
            PLRoleCard(
                roleName = R.string.pl_boy_role,
                lottie = R.raw.boy,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .aspectRatio(1f)
            )
            PLRoleCard(
                roleName = R.string.pl_girl_role,
                lottie = R.raw.girl,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .aspectRatio(1f)
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun PLRoleScreenPreview() {
    PLTheme(darkTheme = true) {
        PLRoleScreen()
    }
}