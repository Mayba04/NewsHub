import { useNavigate } from "react-router-dom";
import { ITagCreate } from "../type"; // Упевніться, що ви правильно імпортуєте тип ITagCreate
import { Button, Form, Input, Row } from "antd";
import { Link } from "react-router-dom";
import http_common from "../../../http_common.ts";

const TagCreatePage = () => {
    const navigate = useNavigate();
    const [form] = Form.useForm<ITagCreate>();

    const onHandlerSubmit = async (values: ITagCreate) => {
        try {
            const formData = new FormData();
            formData.append("name", values.name);
    
            await http_common.post("/api/tags", formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                },
            });
    
            navigate('/tag');
        }
        catch(ex) {
            console.log("Exception create tag", ex);
        }
    }
    

    return (
        <>
            <h1>Додати тег</h1>
            <Row justify="center">
                <Form
                    form={form}
                    onFinish={onHandlerSubmit}
                    layout="vertical"
                    style={{ maxWidth: 600 }}
                >
                    <Form.Item
                        label="Назва"
                        name="name"
                        rules={[
                            { required: true, message: "Це поле є обов'язковим!" },
                            { min: 3, message: "Довжина поля має бути не менше 3 символів" }
                        ]}
                    >
                        <Input autoComplete="off" />
                    </Form.Item>

                    <Row justify="center">
                        <Button type="primary" htmlType="submit" style={{ margin: "0 10px" }}>
                            Додати
                        </Button>
                        <Link to="/tags">
                            <Button style={{ margin: "0 10px" }}>
                                Скасувати
                            </Button>
                        </Link>
                    </Row>
                </Form>
            </Row>
        </>
    );
}

export default TagCreatePage;
