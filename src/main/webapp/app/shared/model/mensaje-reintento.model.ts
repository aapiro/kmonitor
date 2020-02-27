import { Moment } from 'moment';

export interface IMensajeReintento {
  id?: number;
  nombreMicroservicio?: string;
  error?: string;
  fechaHora?: Moment;
  mensaje?: string;
}

export const defaultValue: Readonly<IMensajeReintento> = {};
