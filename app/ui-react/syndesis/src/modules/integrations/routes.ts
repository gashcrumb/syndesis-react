/* tslint:disable:object-literal-sort-keys */
import { include } from 'named-urls';

/**
 * Both the integration creator and editor share the same routes when the creator
 * reaches the third step in the wizard. This object is to keep them DRY.
 */
const editorRoutes = {
  index: 'add-step',
  addConnection: include(':position/connection', {
    selectConnection: '',
    selectAction: `:connectionId`,
    configureAction: `:connectionId/:actionId/:step?`,
  }),
  editConnection: include(':position/edit-connection', {
    selectAction: `select-action/:connectionId`,
    configureAction: `:actionId/:step?`,
  }),
  addStep: include(':position/step', {
    selectStep: '',
    configureStep: `:stepId`,
  }),
  editStep: ':position/edit-step',
  saveAndPublish: 'save',
  root: '',
};

export default include('/integrations', {
  list: '',
  manageCicd: include('manageCicd', { root: '' }),
  create: include('create', {
    start: include('start', {
      selectConnection: '',
      selectAction: `:connectionId`,
      configureAction: `:connectionId/:actionId/:step?`,
    }),
    finish: include('finish', {
      selectConnection: ``,
      selectAction: `:connectionId`,
      configureAction: `:connectionId/:actionId/:step?`,
    }),
    configure: include('configure', editorRoutes),
    root: '',
  }),
  integration: include(':integrationId', {
    details: 'details',
    activity: 'activity',
    metrics: 'metrics',
    edit: include('edit', editorRoutes),
    root: '',
  }),
});
