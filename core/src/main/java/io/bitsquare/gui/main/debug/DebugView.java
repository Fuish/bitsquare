/*
 * This file is part of Bitsquare.
 *
 * Bitsquare is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * Bitsquare is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Bitsquare. If not, see <http://www.gnu.org/licenses/>.
 */

package io.bitsquare.gui.main.debug;

import io.bitsquare.common.taskrunner.Task;
import io.bitsquare.common.viewfx.view.FxmlView;
import io.bitsquare.common.viewfx.view.InitializableView;
import io.bitsquare.trade.protocol.availability.CheckOfferAvailabilityProtocol;
import io.bitsquare.trade.protocol.availability.tasks.ProcessReportOfferAvailabilityMessage;
import io.bitsquare.trade.protocol.availability.tasks.RequestIsOfferAvailable;
import io.bitsquare.trade.protocol.placeoffer.PlaceOfferProtocol;
import io.bitsquare.trade.protocol.placeoffer.tasks.AddOfferToRemoteOfferBook;
import io.bitsquare.trade.protocol.placeoffer.tasks.BroadcastCreateOfferFeeTx;
import io.bitsquare.trade.protocol.placeoffer.tasks.CreateOfferFeeTx;
import io.bitsquare.trade.protocol.placeoffer.tasks.ValidateOffer;
import io.bitsquare.trade.protocol.trade.offerer.OffererAsBuyerProtocol;
import io.bitsquare.trade.protocol.trade.offerer.tasks.OffererCreatesAndSignPayoutTx;
import io.bitsquare.trade.protocol.trade.offerer.tasks.OffererCreatesDepositTxInputs;
import io.bitsquare.trade.protocol.trade.offerer.tasks.OffererProcessPayoutTxPublishedMessage;
import io.bitsquare.trade.protocol.trade.offerer.tasks.OffererProcessRequestDepositTxInputsMessage;
import io.bitsquare.trade.protocol.trade.offerer.tasks.OffererProcessRequestPublishDepositTxMessage;
import io.bitsquare.trade.protocol.trade.offerer.tasks.OffererSendsRequestSellerDepositPaymentMessage;
import io.bitsquare.trade.protocol.trade.offerer.tasks.OffererSendsFiatTransferStartedMessage;
import io.bitsquare.trade.protocol.trade.offerer.tasks.OffererSendsDepositTxPublishedMessage;
import io.bitsquare.trade.protocol.trade.offerer.tasks.OffererSignsAndPublishDepositTx;
import io.bitsquare.trade.protocol.trade.offerer.tasks.OffererVerifiesAndSignsContract;
import io.bitsquare.trade.protocol.trade.offerer.tasks.VerifyTakeOfferFeePayment;
import io.bitsquare.trade.protocol.trade.offerer.tasks.VerifyTakerAccount;
import io.bitsquare.trade.protocol.trade.taker.TakerAsSellerProtocol;
import io.bitsquare.trade.protocol.trade.taker.tasks.TakerCreatesAndSignContract;
import io.bitsquare.trade.protocol.trade.taker.tasks.CreateTakeOfferFeeTx;
import io.bitsquare.trade.protocol.trade.taker.tasks.TakerProcessDepositTxPublishedMessage;
import io.bitsquare.trade.protocol.trade.taker.tasks.TakerProcessFiatTransferStartedMessage;
import io.bitsquare.trade.protocol.trade.taker.tasks.TakerProcessRequestSellerDepositPaymentMessage;
import io.bitsquare.trade.protocol.trade.taker.tasks.TakerSendsPayoutTxPublishedMessage;
import io.bitsquare.trade.protocol.trade.taker.tasks.TakerSendsRequestDepositTxInputsMessage;
import io.bitsquare.trade.protocol.trade.taker.tasks.TakerSendsRequestPublishDepositTxMessage;
import io.bitsquare.trade.protocol.trade.taker.tasks.TakerSignsAndPublishPayoutTx;
import io.bitsquare.trade.protocol.trade.taker.tasks.TakerCommitDepositTx;
import io.bitsquare.trade.protocol.trade.taker.tasks.TakerCreatesAndSignsDepositTx;
import io.bitsquare.trade.protocol.trade.taker.tasks.VerifyOfferFeePayment;
import io.bitsquare.trade.protocol.trade.taker.tasks.VerifyOffererAccount;

import java.util.Arrays;

import javax.inject.Inject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

@FxmlView
public class DebugView extends InitializableView {


    @FXML ComboBox<Class> taskComboBox;
    @FXML CheckBox interceptBeforeCheckBox;

    @Inject
    public DebugView() {
    }

    @Override
    public void initialize() {
        interceptBeforeCheckBox.setSelected(true);

        final ObservableList<Class> items = FXCollections.observableArrayList(Arrays.asList(
                        /*---- Protocol ----*/
                        CheckOfferAvailabilityProtocol.class,
                        io.bitsquare.trade.protocol.availability.tasks.GetPeerAddress.class,
                        RequestIsOfferAvailable.class,
                        ProcessReportOfferAvailabilityMessage.class,
                        Boolean.class, /* used as seperator*/

                        
                        /*---- Protocol ----*/
                        PlaceOfferProtocol.class,
                        ValidateOffer.class,
                        CreateOfferFeeTx.class,
                        AddOfferToRemoteOfferBook.class,
                        BroadcastCreateOfferFeeTx.class,
                        Boolean.class, /* used as seperator*/

                        
                        /*---- Protocol ----*/
                        OffererAsBuyerProtocol.class,
                        OffererProcessRequestDepositTxInputsMessage.class,
                        OffererCreatesDepositTxInputs.class,
                        OffererSendsRequestSellerDepositPaymentMessage.class,

                        OffererProcessRequestPublishDepositTxMessage.class,
                        VerifyTakerAccount.class,
                        OffererVerifiesAndSignsContract.class,
                        OffererSignsAndPublishDepositTx.class,
                        OffererSendsDepositTxPublishedMessage.class,

                        OffererCreatesAndSignPayoutTx.class,
                        VerifyTakeOfferFeePayment.class,
                        OffererSendsFiatTransferStartedMessage.class,

                        OffererProcessPayoutTxPublishedMessage.class,
                        Boolean.class, /* used as seperator*/
                        

                        /*---- Protocol ----*/
                        TakerAsSellerProtocol.class,
                        CreateTakeOfferFeeTx.class,
                        TakerSendsRequestDepositTxInputsMessage.class,

                        TakerProcessRequestSellerDepositPaymentMessage.class,
                        VerifyOffererAccount.class,
                        TakerCreatesAndSignContract.class,
                        TakerCreatesAndSignsDepositTx.class,
                        TakerSendsRequestPublishDepositTxMessage.class,

                        TakerProcessDepositTxPublishedMessage.class,
                        TakerCommitDepositTx.class,

                        TakerProcessFiatTransferStartedMessage.class,

                        TakerSignsAndPublishPayoutTx.class,
                        VerifyOfferFeePayment.class,
                        TakerSendsPayoutTxPublishedMessage.class
                )
        );


        taskComboBox.setVisibleRowCount(items.size());
        taskComboBox.setItems(items);
        taskComboBox.setConverter(new StringConverter<Class>() {
            @Override
            public String toString(Class item) {
                if (item.getSimpleName().contains("Protocol"))
                    return "--- " + item.getSimpleName() + " ---";
                else if (item.getSimpleName().contains("Boolean"))
                    return "";
                else
                    return item.getSimpleName();
            }

            @Override
            public Class fromString(String s) {
                return null;
            }
        });
    }

    @FXML
    void onSelectTask() {
        Class item = taskComboBox.getSelectionModel().getSelectedItem();
        if (!item.getSimpleName().contains("Protocol")) {
            if (interceptBeforeCheckBox.isSelected()) {
                Task.taskToInterceptBeforeRun = item;
                Task.taskToInterceptAfterRun = null;
            }
            else {
                Task.taskToInterceptAfterRun = item;
                Task.taskToInterceptBeforeRun = null;
            }
        }
    }

    @FXML
    void onCheckBoxChanged() {
        onSelectTask();
    }
}

