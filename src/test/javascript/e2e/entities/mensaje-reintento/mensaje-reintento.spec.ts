import { browser, element, by, protractor } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import MensajeReintentoComponentsPage, { MensajeReintentoDeleteDialog } from './mensaje-reintento.page-object';
import MensajeReintentoUpdatePage from './mensaje-reintento-update.page-object';
import {
  waitUntilDisplayed,
  waitUntilAnyDisplayed,
  click,
  getRecordsCount,
  waitUntilHidden,
  waitUntilCount,
  isVisible
} from '../../util/utils';

const expect = chai.expect;

describe('MensajeReintento e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let mensajeReintentoComponentsPage: MensajeReintentoComponentsPage;
  let mensajeReintentoUpdatePage: MensajeReintentoUpdatePage;
  let mensajeReintentoDeleteDialog: MensajeReintentoDeleteDialog;
  let beforeRecordsCount = 0;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.waitUntilDisplayed();

    await signInPage.username.sendKeys('admin');
    await signInPage.password.sendKeys('admin');
    await signInPage.loginButton.click();
    await signInPage.waitUntilHidden();
    await waitUntilDisplayed(navBarPage.entityMenu);
    await waitUntilDisplayed(navBarPage.adminMenu);
    await waitUntilDisplayed(navBarPage.accountMenu);
  });

  it('should load MensajeReintentos', async () => {
    await navBarPage.getEntityPage('mensaje-reintento');
    mensajeReintentoComponentsPage = new MensajeReintentoComponentsPage();
    expect(await mensajeReintentoComponentsPage.title.getText()).to.match(/Mensaje Reintentos/);

    expect(await mensajeReintentoComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilAnyDisplayed([mensajeReintentoComponentsPage.noRecords, mensajeReintentoComponentsPage.table]);

    beforeRecordsCount = (await isVisible(mensajeReintentoComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(mensajeReintentoComponentsPage.table);
  });

  it('should load create MensajeReintento page', async () => {
    await mensajeReintentoComponentsPage.createButton.click();
    mensajeReintentoUpdatePage = new MensajeReintentoUpdatePage();
    expect(await mensajeReintentoUpdatePage.getPageTitle().getAttribute('id')).to.match(
      /kafkaMonitorApp.mensajeReintento.home.createOrEditLabel/
    );
    await mensajeReintentoUpdatePage.cancel();
  });

  it('should create and save MensajeReintentos', async () => {
    await mensajeReintentoComponentsPage.createButton.click();
    await mensajeReintentoUpdatePage.setNombreMicroservicioInput('nombreMicroservicio');
    expect(await mensajeReintentoUpdatePage.getNombreMicroservicioInput()).to.match(/nombreMicroservicio/);
    await mensajeReintentoUpdatePage.setErrorInput('error');
    expect(await mensajeReintentoUpdatePage.getErrorInput()).to.match(/error/);
    await mensajeReintentoUpdatePage.setFechaHoraInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await mensajeReintentoUpdatePage.getFechaHoraInput()).to.contain('2001-01-01T02:30');
    await mensajeReintentoUpdatePage.setMensajeInput('mensaje');
    expect(await mensajeReintentoUpdatePage.getMensajeInput()).to.match(/mensaje/);
    await waitUntilDisplayed(mensajeReintentoUpdatePage.saveButton);
    await mensajeReintentoUpdatePage.save();
    await waitUntilHidden(mensajeReintentoUpdatePage.saveButton);
    expect(await isVisible(mensajeReintentoUpdatePage.saveButton)).to.be.false;

    expect(await mensajeReintentoComponentsPage.createButton.isEnabled()).to.be.true;

    await waitUntilDisplayed(mensajeReintentoComponentsPage.table);

    await waitUntilCount(mensajeReintentoComponentsPage.records, beforeRecordsCount + 1);
    expect(await mensajeReintentoComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);
  });

  it('should delete last MensajeReintento', async () => {
    const deleteButton = mensajeReintentoComponentsPage.getDeleteButton(mensajeReintentoComponentsPage.records.last());
    await click(deleteButton);

    mensajeReintentoDeleteDialog = new MensajeReintentoDeleteDialog();
    await waitUntilDisplayed(mensajeReintentoDeleteDialog.deleteModal);
    expect(await mensajeReintentoDeleteDialog.getDialogTitle().getAttribute('id')).to.match(
      /kafkaMonitorApp.mensajeReintento.delete.question/
    );
    await mensajeReintentoDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(mensajeReintentoDeleteDialog.deleteModal);

    expect(await isVisible(mensajeReintentoDeleteDialog.deleteModal)).to.be.false;

    await waitUntilAnyDisplayed([mensajeReintentoComponentsPage.noRecords, mensajeReintentoComponentsPage.table]);

    const afterCount = (await isVisible(mensajeReintentoComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(mensajeReintentoComponentsPage.table);
    expect(afterCount).to.eq(beforeRecordsCount);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
