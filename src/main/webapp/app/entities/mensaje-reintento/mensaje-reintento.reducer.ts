import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMensajeReintento, defaultValue } from 'app/shared/model/mensaje-reintento.model';

export const ACTION_TYPES = {
  FETCH_MENSAJEREINTENTO_LIST: 'mensajeReintento/FETCH_MENSAJEREINTENTO_LIST',
  FETCH_MENSAJEREINTENTO: 'mensajeReintento/FETCH_MENSAJEREINTENTO',
  CREATE_MENSAJEREINTENTO: 'mensajeReintento/CREATE_MENSAJEREINTENTO',
  UPDATE_MENSAJEREINTENTO: 'mensajeReintento/UPDATE_MENSAJEREINTENTO',
  DELETE_MENSAJEREINTENTO: 'mensajeReintento/DELETE_MENSAJEREINTENTO',
  RESET: 'mensajeReintento/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMensajeReintento>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type MensajeReintentoState = Readonly<typeof initialState>;

// Reducer

export default (state: MensajeReintentoState = initialState, action): MensajeReintentoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MENSAJEREINTENTO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MENSAJEREINTENTO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_MENSAJEREINTENTO):
    case REQUEST(ACTION_TYPES.UPDATE_MENSAJEREINTENTO):
    case REQUEST(ACTION_TYPES.DELETE_MENSAJEREINTENTO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_MENSAJEREINTENTO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MENSAJEREINTENTO):
    case FAILURE(ACTION_TYPES.CREATE_MENSAJEREINTENTO):
    case FAILURE(ACTION_TYPES.UPDATE_MENSAJEREINTENTO):
    case FAILURE(ACTION_TYPES.DELETE_MENSAJEREINTENTO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_MENSAJEREINTENTO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_MENSAJEREINTENTO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_MENSAJEREINTENTO):
    case SUCCESS(ACTION_TYPES.UPDATE_MENSAJEREINTENTO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_MENSAJEREINTENTO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/mensaje-reintentos';

// Actions

export const getEntities: ICrudGetAllAction<IMensajeReintento> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_MENSAJEREINTENTO_LIST,
    payload: axios.get<IMensajeReintento>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IMensajeReintento> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MENSAJEREINTENTO,
    payload: axios.get<IMensajeReintento>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IMensajeReintento> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MENSAJEREINTENTO,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMensajeReintento> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MENSAJEREINTENTO,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMensajeReintento> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MENSAJEREINTENTO,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
