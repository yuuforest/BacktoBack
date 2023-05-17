import React, { useEffect, useState } from "react";
import { Button } from 'primereact/button';
import { Dialog } from 'primereact/dialog';
import { DataTable } from "primereact/datatable";
import { Column } from "primereact/column";
import axios from "axios";

function PointModal() {
    const [visible, setVisible] = useState(false);
    const [infos, setInfos] = useState([]);

    const getPoingLogs = async () => {
        try{
            await axios
                .get("http://k8a708.p.ssafy.io/api/point/point-log/member/" + 1)
                .then((response) => {
                    console.log(response);
                    console.log(response.data);
                    setInfos(response.data);
                })
        } catch (error) {
            console.log("Point Log Error....");
        }
    }

    useEffect(() => {
        getPoingLogs();
    }, []);

    return (
        <div className="card flex justify-content-center">
            <Button label="포인트 이력" icon="pi pi-external-link" onClick={() => setVisible(true)} />
            <Dialog header="포인트 내역" visible={visible} style={{ width: '50vw' }} onHide={() => setVisible(false)}>
                <DataTable value={infos}>
                    <Column field="num" header="번호"></Column>
                    <Column field="point" header="포인트"></Column>
                    <Column field="detail" header="상세 내역 "></Column>
                    <Column field="time" header="날짜"></Column>
                </DataTable>
            </Dialog>
        </div>
    )
}

export default PointModal;