import { element, by, ElementFinder } from 'protractor';

export default class MensajeReintentoUpdatePage {
  pageTitle: ElementFinder = element(by.id('kafkaMonitorApp.mensajeReintento.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  nombreMicroservicioInput: ElementFinder = element(by.css('input#mensaje-reintento-nombreMicroservicio'));
  errorInput: ElementFinder = element(by.css('input#mensaje-reintento-error'));
  fechaHoraInput: ElementFinder = element(by.css('input#mensaje-reintento-fechaHora'));
  mensajeInput: ElementFinder = element(by.css('input#mensaje-reintento-mensaje'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setNombreMicroservicioInput(nombreMicroservicio) {
    await this.nombreMicroservicioInput.sendKeys(nombreMicroservicio);
  }

  async getNombreMicroservicioInput() {
    return this.nombreMicroservicioInput.getAttribute('value');
  }

  async setErrorInput(error) {
    await this.errorInput.sendKeys(error);
  }

  async getErrorInput() {
    return this.errorInput.getAttribute('value');
  }

  async setFechaHoraInput(fechaHora) {
    await this.fechaHoraInput.sendKeys(fechaHora);
  }

  async getFechaHoraInput() {
    return this.fechaHoraInput.getAttribute('value');
  }

  async setMensajeInput(mensaje) {
    await this.mensajeInput.sendKeys(mensaje);
  }

  async getMensajeInput() {
    return this.mensajeInput.getAttribute('value');
  }

  async save() {
    await this.saveButton.click();
  }

  async cancel() {
    await this.cancelButton.click();
  }

  getSaveButton() {
    return this.saveButton;
  }
}
