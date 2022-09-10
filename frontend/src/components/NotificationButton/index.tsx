import axios from "axios";
import icon from "../../assets/img/notification-icon.svg";
import { BASE_URL } from "../../utils/request";
import './styles.css';

type Props = {
    saleId: number;
}

function NotificationButton({ saleId }: Props) {
    return (
        <div className="dsmeta-red-btn" onClick={() => handleClick(saleId)}>
            <img src={icon} alt="Notificar" />
        </div>
    )
}

export default NotificationButton;

function handleClick(saleId: number): void {
    axios(`${BASE_URL}/sales/${saleId}/notifications`)
        .then(response => {
            console.log("SUCESSO");
        });
}
