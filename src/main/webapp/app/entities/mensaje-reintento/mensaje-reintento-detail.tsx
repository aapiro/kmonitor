import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './mensaje-reintento.reducer';
import { IMensajeReintento } from 'app/shared/model/mensaje-reintento.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMensajeReintentoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MensajeReintentoDetail = (props: IMensajeReintentoDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { mensajeReintentoEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="kafkaMonitorApp.mensajeReintento.detail.title">MensajeReintento</Translate> [
          <b>{mensajeReintentoEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="nombreMicroservicio">
              <Translate contentKey="kafkaMonitorApp.mensajeReintento.nombreMicroservicio">Nombre Microservicio</Translate>
            </span>
          </dt>
          <dd>{mensajeReintentoEntity.nombreMicroservicio}</dd>
          <dt>
            <span id="error">
              <Translate contentKey="kafkaMonitorApp.mensajeReintento.error">Error</Translate>
            </span>
          </dt>
          <dd>{mensajeReintentoEntity.error}</dd>
          <dt>
            <span id="fechaHora">
              <Translate contentKey="kafkaMonitorApp.mensajeReintento.fechaHora">Fecha Hora</Translate>
            </span>
          </dt>
          <dd>
            <TextFormat value={mensajeReintentoEntity.fechaHora} type="date" format={APP_DATE_FORMAT} />
          </dd>
          <dt>
            <span id="mensaje">
              <Translate contentKey="kafkaMonitorApp.mensajeReintento.mensaje">Mensaje</Translate>
            </span>
          </dt>
          <dd>{mensajeReintentoEntity.mensaje}</dd>
        </dl>
        <Button tag={Link} to="/mensaje-reintento" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/mensaje-reintento/${mensajeReintentoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ mensajeReintento }: IRootState) => ({
  mensajeReintentoEntity: mensajeReintento.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MensajeReintentoDetail);
