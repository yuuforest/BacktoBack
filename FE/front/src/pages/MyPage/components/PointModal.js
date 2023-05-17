import React, { useEffect, useState } from "react";
import { Button } from 'primereact/button';
import { Dialog } from 'primereact/dialog';
import { DataTable } from "primereact/datatable";
import { Column } from "primereact/column";

function PointModal() {
    const [visible, setVisible] = useState(false);
    const [infos, setInfos] = useState([]);

    var data = {
        "number": 1,
        "my-point": 200,
        "detail": "BET",
        "date": "2023-09-09",
    };

    useEffect(() => {
        setInfos([data]);
    }, []);

    return (
        <div className="card flex justify-content-center">
            <Button label="포인트 이력" icon="pi pi-external-link" onClick={() => setVisible(true)} />
            <Dialog header="포인트 내역" visible={visible} style={{ width: '50vw' }} onHide={() => setVisible(false)}>
                <DataTable value={infos}>
                    <Column field="number" header="번호"></Column>
                    <Column field="my-point" header="포인트"></Column>
                    <Column field="detail" header="상세 내역 "></Column>
                    <Column field="date" header="날짜"></Column>
                </DataTable>
            </Dialog>
        </div>
    )
}

export default PointModal;