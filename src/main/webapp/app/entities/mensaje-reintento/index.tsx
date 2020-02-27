import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MensajeReintento from './mensaje-reintento';
import MensajeReintentoDetail from './mensaje-reintento-detail';
import MensajeReintentoUpdate from './mensaje-reintento-update';
import MensajeReintentoDeleteDialog from './mensaje-reintento-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MensajeReintentoDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MensajeReintentoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MensajeReintentoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MensajeReintentoDetail} />
      <ErrorBoundaryRoute path={match.url} component={MensajeReintento} />
    </Switch>
  </>
);

export default Routes;
