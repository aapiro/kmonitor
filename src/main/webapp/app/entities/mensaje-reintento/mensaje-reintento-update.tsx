import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './mensaje-reintento.reducer';
import { IMensajeReintento } from 'app/shared/model/mensaje-reintento.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMensajeReintentoUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MensajeReintentoUpdate = (props: IMensajeReintentoUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { mensajeReintentoEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/mensaje-reintento' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.fechaHora = convertDateTimeToServer(values.fechaHora);

    if (errors.length === 0) {
      const entity = {
        ...mensajeReintentoEntity,
        ...values
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="kafkaMonitorApp.mensajeReintento.home.createOrEditLabel">
            <Translate contentKey="kafkaMonitorApp.mensajeReintento.home.createOrEditLabel">Create or edit a MensajeReintento</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : mensajeReintentoEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="mensaje-reintento-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="mensaje-reintento-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nombreMicroservicioLabel" for="mensaje-reintento-nombreMicroservicio">
                  <Translate contentKey="kafkaMonitorApp.mensajeReintento.nombreMicroservicio">Nombre Microservicio</Translate>
                </Label>
                <AvField id="mensaje-reintento-nombreMicroservicio" type="text" name="nombreMicroservicio" />
              </AvGroup>
              <AvGroup>
                <Label id="errorLabel" for="mensaje-reintento-error">
                  <Translate contentKey="kafkaMonitorApp.mensajeReintento.error">Error</Translate>
                </Label>
                <AvField id="mensaje-reintento-error" type="text" name="error" />
              </AvGroup>
              <AvGroup>
                <Label id="fechaHoraLabel" for="mensaje-reintento-fechaHora">
                  <Translate contentKey="kafkaMonitorApp.mensajeReintento.fechaHora">Fecha Hora</Translate>
                </Label>
                <AvInput
                  id="mensaje-reintento-fechaHora"
                  type="datetime-local"
                  className="form-control"
                  name="fechaHora"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.mensajeReintentoEntity.fechaHora)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="mensajeLabel" for="mensaje-reintento-mensaje">
                  <Translate contentKey="kafkaMonitorApp.mensajeReintento.mensaje">Mensaje</Translate>
                </Label>
                <AvField id="mensaje-reintento-mensaje" type="text" name="mensaje" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/mensaje-reintento" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  mensajeReintentoEntity: storeState.mensajeReintento.entity,
  loading: storeState.mensajeReintento.loading,
  updating: storeState.mensajeReintento.updating,
  updateSuccess: storeState.mensajeReintento.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MensajeReintentoUpdate);
