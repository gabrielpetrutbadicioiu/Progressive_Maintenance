package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signIn.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.Company

@Composable
fun PickCompanyAlertDialog(
    onDismissRequest:()->Unit,
    companies:List<Company>,
    filteredCompanies:List<Company>,
    query:String,
    onQueryChange:(String)->Unit,
    onCompanyClick:(Company)->Unit
)
{
    val displayedCompanies=if (query.isEmpty()) companies else filteredCompanies
    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        confirmButton = {  },
        modifier = Modifier.fillMaxSize(),
        text = {

            Column(modifier = Modifier)
            {
                CompanySearchArea(
                    query =query,
                    onQueryChange = {newValue->onQueryChange(newValue)},
                    onCancelClick = {onDismissRequest()}
                )
                HorizontalDivider(
                    modifier = Modifier.padding(8.dp),
                    thickness = 4.dp,
                    color = colorResource(id = R.color.unfocused_color)
                )
                if (displayedCompanies.isEmpty())
                {
                    Text(text = stringResource(id = R.string.company_searched_fail))
                }
                else{
                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(displayedCompanies)
                        {company->
                            CompanyDescription(companyName = company.organisationName, avatar = company.companyLogoUrl) {
                                onCompanyClick(company)
                            }
                        }
                    }
                }

            }
        })
}