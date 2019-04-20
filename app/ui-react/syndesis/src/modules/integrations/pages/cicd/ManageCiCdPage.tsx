import {
  Breadcrumb,
  CiCdManagePageUI,
  IFilterType,
  ISortType,
} from '@syndesis/ui';
import { WithListViewToolbarHelpers } from '@syndesis/utils';
import * as React from 'react';
import { Translation } from 'react-i18next';
import { Link } from 'react-router-dom';
import i18n from '../../../../i18n';
import { PageTitle } from '../../../../shared';
import resolvers from '../../resolvers';

const filterByName = {
  filterType: 'text',
  id: 'name',
  placeholder: i18n.t('shared:filterByNamePlaceholder'),
  title: i18n.t('shared:Name'),
} as IFilterType;

const filterTypes = [filterByName];

const sortByName = {
  id: 'name',
  isNumeric: false,
  title: i18n.t('shared:Name'),
} as ISortType;

const sortTypes: ISortType[] = [sortByName];

function createConfirmRemoveString(name: string) {
  return i18n.t('integrations:ConfirmRemoveTag', { tag: name });
}

export class ManageCiCdPage extends React.Component {
  constructor(props: any) {
    super(props);
    this.handleAddItem = this.handleAddItem.bind(this);
    this.handleEditItem = this.handleEditItem.bind(this);
    this.handleRemoveItem = this.handleRemoveItem.bind(this);
  }
  public handleEditItem(name: string) {
    /* todo */
  }
  public handleRemoveItem(name: string) {
    /* todo */
  }
  public handleAddItem(name: string) {
    /* todo */
  }
  public render() {
    return (
      <Translation ns={['integrations', 'shared']}>
        {t => (
          <WithListViewToolbarHelpers
            defaultFilterType={filterByName}
            defaultSortType={sortByName}
          >
            {helpers => (
              <>
                <PageTitle title={t('integrations:ManageCiCd')} />
                <Breadcrumb>
                  <Link to={resolvers.list()}>{t('shared:Integrations')}</Link>
                  <span>{t('integrations:ManageCiCd')}</span>
                </Breadcrumb>
                <CiCdManagePageUI
                  onEditItem={this.handleEditItem}
                  onAddItem={this.handleAddItem}
                  onRemoveItem={this.handleRemoveItem}
                  filterTypes={filterTypes}
                  sortTypes={sortTypes}
                  {...helpers}
                  resultsCount={0}
                  i18nResultsCount={t('shared:resultsCount', {
                    count: 0,
                  })}
                  i18nRemoveButtonText={t('shared:Remove')}
                  i18nAddNewButtonText={t('shared:AddNew')}
                  i18nPageTitle={t('integrations:ManageCiCd')}
                  i18nCancelButtonText={t('shared:Cancel')}
                  i18nSaveButtonText={t('shared:Save')}
                  i18nEditButtonText={t('shared:Edit')}
                  i18nConfirmRemoveButtonText={t('shared:Yes')}
                  i18nConfirmCancelButtonText={t('shared:No')}
                  i18nRemoveConfirmationMessage={createConfirmRemoveString}
                  i18nRemoveConfirmationTitle={t('shared:ConfirmRemove')}
                  i18nRemoveConfirmationDetailMessage={t(
                    'integrations:ConfirmRemoveTagDetail;'
                  )}
                  i18nAddTagDialogTitle={t('integrations:AddTagTitle')}
                  i18nAddTagDialogDescription={t(
                    'integrations:AddTagDescription'
                  )}
                  i18nEditTagDialogTitle={t('integrations:EditTagTitle')}
                  i18nEditTagDialogDescription={t(
                    'integrations:EditTagDescription'
                  )}
                  i18nTagInputLabel={t('integrations:TagName')}
                  i18nPageDescription={t('integrations:ManageCiCdDescription')}
                  i18nEmptyStateTitle={t(
                    'integrations:NoEnvironmentsAvailable'
                  )}
                  listItems={[]}
                />
              </>
            )}
          </WithListViewToolbarHelpers>
        )}
      </Translation>
    );
  }
}
